'use strict';

const {assertLoopbackHost, READ_ONLY_METHODS, bridgeError} = require('./protocol.js');

function assertPort(port) {
  if (!Number.isInteger(port) || port < 1 || port > 65535) throw bridgeError('INVALID_PORT');
  return port;
}

function buildBridgeUrl(options = {}) {
  const host = assertLoopbackHost(options.host);
  const port = assertPort(options.port);
  const authority = host.includes(':') ? `[${host}]` : host;
  return `ws://${authority}:${port}/bridge`;
}

function buildHandshake(options = {}) {
  const session = options.session || {};
  if (typeof session.protocolVersion !== 'string' || typeof session.sessionId !== 'string' || typeof session.token !== 'string') {
    throw bridgeError('INVALID_SESSION');
  }
  const capabilities = Array.isArray(options.capabilities) ? options.capabilities : [];
  if (capabilities.some((method) => !READ_ONLY_METHODS.includes(method))) throw bridgeError('METHOD_NOT_ALLOWED');
  const fingerprint = options.fingerprint && typeof options.fingerprint === 'object' && !Array.isArray(options.fingerprint)
    ? options.fingerprint : {};
  return {
    type: 'handshake',
    protocolVersion: session.protocolVersion,
    sessionId: session.sessionId,
    token: session.token,
    capabilities: [...capabilities],
    fingerprint,
  };
}

module.exports = {buildBridgeUrl, buildHandshake, assertPort};
