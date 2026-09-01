# Security Policy

## Supported versions

This repository is under active development. Security fixes are maintained for the current `main` branch targeting NeoForge 1.21.1 and Java 21. Older commits, development snapshots, and abandoned branches are not supported unless explicitly stated otherwise.

## Reporting a vulnerability

Please do not disclose exploitable security details in a public issue, pull request, discussion, or commit message.

Prefer GitHub's private vulnerability reporting / Security Advisory flow when it is available for this repository. Include, when applicable:

- the affected commit, workflow, release, or component;
- a minimal reproduction;
- expected and observed behavior;
- security impact and realistic attack prerequisites;
- logs or stack traces with secrets and personal data removed;
- a proposed mitigation, if known.

If a private reporting option is not available, open a minimal public issue requesting a private reporting channel, but do not include exploit steps, credentials, tokens, private data, or weaponized proof-of-concept material.

## Scope

Security reports are particularly useful for issues involving:

- credential, token, or secret exposure;
- unsafe GitHub Actions permissions or supply-chain behavior;
- arbitrary code execution or command injection;
- unsafe deserialization, file access, or network behavior;
- privilege or authority bypasses in server-side gameplay/runtime code;
- dependency or build-pipeline compromise;
- vulnerabilities that can affect a dedicated server, client, or multiplayer environment.

Gameplay balance problems, ordinary crashes without a security boundary impact, feature requests, and compatibility bugs should use the normal issue workflow.

## Disclosure

Please allow time for validation and remediation before public disclosure. Once a fix is available, the repository may publish an advisory or release notes describing the affected versions, impact, and remediation without exposing unnecessary exploit detail.