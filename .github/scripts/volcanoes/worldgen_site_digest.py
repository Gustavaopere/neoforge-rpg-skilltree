#!/usr/bin/env python3
import gzip
import hashlib
import json
import struct
import sys


class Reader:
    def __init__(self, data):
        self.data = memoryview(data)
        self.offset = 0

    def read(self, size):
        end = self.offset + size
        if end > len(self.data):
            raise ValueError("truncated NBT payload")
        chunk = self.data[self.offset:end]
        self.offset = end
        return chunk

    def unpack(self, fmt):
        size = struct.calcsize(fmt)
        return struct.unpack(fmt, self.read(size))[0]

    def string(self):
        length = self.unpack(">H")
        return bytes(self.read(length)).decode("utf-8")


def payload(reader, tag_type):
    if tag_type == 1:
        return reader.unpack(">b")
    if tag_type == 2:
        return reader.unpack(">h")
    if tag_type == 3:
        return reader.unpack(">i")
    if tag_type == 4:
        return reader.unpack(">q")
    if tag_type == 5:
        return reader.unpack(">f")
    if tag_type == 6:
        return reader.unpack(">d")
    if tag_type == 7:
        length = reader.unpack(">i")
        return list(bytes(reader.read(length)))
    if tag_type == 8:
        return reader.string()
    if tag_type == 9:
        element_type = reader.unpack(">B")
        length = reader.unpack(">i")
        return [payload(reader, element_type) for _ in range(length)]
    if tag_type == 10:
        result = {}
        while True:
            child_type = reader.unpack(">B")
            if child_type == 0:
                return result
            name = reader.string()
            result[name] = payload(reader, child_type)
    if tag_type == 11:
        length = reader.unpack(">i")
        return [reader.unpack(">i") for _ in range(length)]
    if tag_type == 12:
        length = reader.unpack(">i")
        return [reader.unpack(">q") for _ in range(length)]
    raise ValueError(f"unsupported NBT tag type {tag_type}")


def read_root(path):
    with gzip.open(path, "rb") as stream:
        reader = Reader(stream.read())
    root_type = reader.unpack(">B")
    if root_type != 10:
        raise ValueError(f"expected compound root, got {root_type}")
    reader.string()  # root name
    return payload(reader, root_type)


def canonical_sites(root):
    data = root.get("data", root)
    sites = data.get("sites")
    if not isinstance(sites, list) or not sites:
        raise ValueError("volcanoes_sites.dat contains no persisted sites")

    stable = []
    for site in sites:
        stable.append({
            "id": site["id"],
            "center": site["center"],
            "type": site["type"],
            "tectonic_context": site["tectonic_context"],
            "plate_id": site["plate_id"],
            "neighbor_plate_id": site["neighbor_plate_id"],
            "initial_volcanic_potential": site["initial_volcanic_potential"],
        })
    stable.sort(key=lambda site: json.dumps(site["id"], separators=(",", ":")))
    return stable


def main():
    if len(sys.argv) != 2:
        raise SystemExit("usage: worldgen_site_digest.py <volcanoes_sites.dat>")
    sites = canonical_sites(read_root(sys.argv[1]))
    encoded = json.dumps(sites, sort_keys=True, separators=(",", ":")).encode("utf-8")
    print(hashlib.sha256(encoded).hexdigest())


if __name__ == "__main__":
    main()
