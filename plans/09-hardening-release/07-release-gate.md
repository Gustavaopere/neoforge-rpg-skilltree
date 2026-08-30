# Hardening Plan — Final Release Gate

**Goal:** estabelecer condições objetivas para considerar um build publicável.

- [ ] CI verde no commit candidato.
- [ ] Build NeoForge e JAR verification verdes.
- [ ] Dedicated-server smoke verde.
- [ ] Nenhum blocker conhecido de crash/save corruption.
- [ ] Matriz de compatibilidade do escopo fechada.
- [ ] Migrações exigidas testadas.
- [ ] Wiki/changelog atualizados.
- [ ] `SOURCES.md` e `THIRD_PARTY_NOTICES.md` correspondem ao código/assets realmente usados.
- [ ] Auditoria `08-third-party-licenses-provenance.md` concluída para todo material efetivamente copiado/adaptado/distribuído.
- [ ] Nenhum `DERIVED_CODE`/`DERIVED_ASSET` distribuído com licença `UNKNOWN`, `REVIEW_REQUIRED` ou `PERMISSION_REQUIRED` sem resolução/evidência adequada.
- [ ] Notices/copyright/source obligations exigidos por MIT/GPL/LGPL/licenças custom aplicáveis estão satisfeitos.
- [ ] Assets All Rights Reserved não foram copiados/adaptados sem permissão específica.
- [ ] Version/tag/release artifact correspondem ao mesmo commit.

**Regra fail-closed:** o fato de um mod/repositório ser público ou de o projeto ser para uso pessoal não autoriza publicar cópias de código/assets em desacordo com a licença upstream.

**Acceptance:** existe um único commit candidato reproduzível que satisfaz todos os gates obrigatórios, inclusive provenance/licenças de terceiros.