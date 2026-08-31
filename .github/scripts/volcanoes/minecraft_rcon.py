#!/usr/bin/env python3
"""Tiny Minecraft RCON client used only by the worldgen compatibility CI harness."""

from __future__ import annotations

import socket
import struct
import sys
import time

AUTH_TIMEOUT_SECONDS = 30
COMMAND_TIMEOUT_SECONDS = 300


def packet(request_id: int, packet_type: int, payload: str) -> bytes:
    encoded = payload.encode("utf-8")
    body = struct.pack("<ii", request_id, packet_type) + encoded + b"\x00\x00"
    return struct.pack("<i", len(body)) + body


def read_exact(sock: socket.socket, size: int) -> bytes:
    chunks: list[bytes] = []
    remaining = size
    while remaining:
        chunk = sock.recv(remaining)
        if not chunk:
            raise EOFError("RCON connection closed before a complete response")
        chunks.append(chunk)
        remaining -= len(chunk)
    return b"".join(chunks)


def read_packet(sock: socket.socket) -> tuple[int, int, str]:
    (length,) = struct.unpack("<i", read_exact(sock, 4))
    if length < 10 or length > 4 * 1024 * 1024:
        raise ValueError(f"invalid RCON packet length: {length}")
    body = read_exact(sock, length)
    request_id, packet_type = struct.unpack("<ii", body[:8])
    payload = body[8:-2].decode("utf-8", errors="replace")
    return request_id, packet_type, payload


def connect_with_retry(host: str, port: int, attempts: int = 60) -> socket.socket:
    last_error: OSError | None = None
    for _ in range(attempts):
        try:
            sock = socket.create_connection((host, port), timeout=5)
            sock.settimeout(AUTH_TIMEOUT_SECONDS)
            return sock
        except OSError as exc:
            last_error = exc
            time.sleep(1)
    raise ConnectionError(f"RCON did not become reachable at {host}:{port}") from last_error


def main() -> int:
    if len(sys.argv) < 5:
        print("usage: minecraft_rcon.py HOST PORT PASSWORD COMMAND...", file=sys.stderr)
        return 2

    host = sys.argv[1]
    port = int(sys.argv[2])
    password = sys.argv[3]
    command = " ".join(sys.argv[4:])

    with connect_with_retry(host, port) as sock:
        sock.sendall(packet(1, 3, password))
        auth_id, _, auth_payload = read_packet(sock)
        if auth_id == -1:
            raise PermissionError("Minecraft RCON authentication failed")
        if auth_id != 1:
            raise RuntimeError(f"unexpected RCON auth response id {auth_id}: {auth_payload}")

        sock.settimeout(COMMAND_TIMEOUT_SECONDS)
        sock.sendall(packet(2, 2, command))
        try:
            response_id, _, response = read_packet(sock)
        except EOFError:
            if command.strip().lower() == "stop":
                return 0
            raise
        if response_id != 2:
            raise RuntimeError(f"unexpected RCON command response id {response_id}")
        if response:
            print(response)
    return 0


if __name__ == "__main__":
    raise SystemExit(main())
