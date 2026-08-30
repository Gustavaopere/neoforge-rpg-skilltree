# 09 — Hardening & Release

Transformar o conjunto funcional em release confiável: testes reproduzíveis, performance medida, saves migráveis, compatibilidade explícita, documentação correspondente ao jogo e compliance de terceiros auditável para o repositório público.

Ordem funcional: matriz de testes → performance → saves/upgrades → compatibilidade → network/data hardening → docs de release → auditoria contínua de licenças/proveniência → gate final.

Arquivos:

1. `01-test-matrix.md`
2. `02-performance.md`
3. `03-save-migrations.md`
4. `04-compatibility-matrix.md`
5. `05-network-data-hardening.md`
6. `06-release-docs.md`
7. `07-release-gate.md`
8. `08-third-party-licenses-provenance.md`

`08-third-party-licenses-provenance.md` foi introduzido depois do gate histórico `07`, mas é **pré-condição obrigatória** para considerar o `07-release-gate.md` satisfeito. O número do arquivo não autoriza fechar release antes da auditoria de terceiros.