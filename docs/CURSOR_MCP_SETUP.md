# Cursor MCP setup — Windows

This repository carries a project-scoped Cursor MCP configuration in `.cursor/mcp.json`.

## Enabled services

- **GitHub MCP Server (official)** — repository, pull request, issue, Actions/CI and GitHub security operations.
- **Context7** — current library/API documentation used for version-sensitive implementation work.
- **DeepWiki** — architecture and repository-level research for public upstream/provider repositories.
- **BetterMemory** — persistent coding-agent memory validated against files and Git history; registered globally by the bootstrap.

SonarQube is intentionally **not duplicated as a local MCP**. This repository already runs SonarQube Cloud through `.github/workflows/sonarqube.yml` with its repository secret and quality gate.

## One-click bootstrap

On the Windows development machine, double-click:

`tools\setup-cursor-mcps.cmd`

The launcher runs the PowerShell bootstrap, which:

1. downloads the pinned official GitHub MCP Server release for the current Windows architecture;
2. verifies the downloaded archive against GitHub's published SHA-256 digest/checksum before installing it;
3. validates the selected GitHub toolsets locally;
4. installs `uv` if it is missing;
5. installs Python 3.13 through `uv`, installs BetterMemory in an isolated tool environment and idempotently registers BetterMemory with Cursor;
6. runs `bettermemory doctor`.

No GitHub PAT, Sonar token, Context7 key or other credential is committed by this setup.

## First GitHub use

The project uses the official native GitHub MCP binary over stdio. On the first GitHub MCP action, the official binary opens GitHub in the browser for OAuth authorization. The resulting token is kept in memory by the MCP process rather than written into this repository.

After the bootstrap finishes, restart Cursor completely. Context7 and DeepWiki are project-scoped and load from `.cursor/mcp.json`; BetterMemory is merged into Cursor's global MCP configuration by its own supported initializer.

## Maintenance

The GitHub MCP bootstrap is pinned deliberately rather than downloading an unreviewed future release automatically. Upgrade the `GitHubMcpVersion` constant in `tools/setup-cursor-mcps.ps1` only after checking the new official release and its configuration changes.

Do not add generic community GitHub MCP servers unless a concrete missing capability is identified. Duplicate GitHub tool surfaces increase context cost and make tool selection less deterministic.
