# 00 — Cânone

Contém as regras editoriais versionadas que todos os demais arquivos de `historia/` devem respeitar.

## Authority e representação

- **Grimoire/TTRPG.bot** é a authority principal para lore estruturada/Campaign Bible/Foundations quando o domínio estiver registrado ali.
- **`historia/` em GitHub** é a fonte editorial versionada: IDs estáveis, documentos revisáveis, decisões registradas, diff, histórico e material aceito no repositório.
- **Stage 08 / runtime** governa persistência, transições de estado, idempotência e efeitos executáveis.
- **modlist/JARs/providers** governam capabilities mecânicas reais.

Uma divergência Grimoire↔GitHub deve ser preservada e reconciliada por domínio/proveniência; nenhuma das superfícies deve ser sobrescrita cegamente.

Se uma decisão de lore central depender do Grimoire e a conexão estiver indisponível, a decisão permanece pendente/fail-closed. Ausência de informação não autoriza preencher cânone por suposição.

## Arquivos

- `cronologia-mestra.md` — macroperíodos históricos, 14 eras de progressão e relação entre cronologia editorial e discovery.
- `regras-do-mundo.md` — invariantes narrativos/sistêmicos transversais.
- `politica-sem-spoilers.md` — política de exposição em chat/status e superfícies player-facing.
- `relacoes-memoria-e-identidade.md` — contrato editorial para relações multidimensionais, memória e continuidade de identidade.

`relacoes-memoria-e-identidade.md` espelha os contratos técnicos do Stage 08 para relações multidimensionais, memória social, grievance/debt/favor e continuidade após morte/retorno. Ele não define ranges numéricos nem persistence de runtime.

## Mudança de cânone

Alterações de cânone devem ser deliberadas, rastreáveis e reconciliadas com as authorities aplicáveis.

Merge em Git registra a versão editorial aceita do arquivo; não transforma sozinho uma hipótese conflitante em verdade de Campaign Bible. Da mesma forma, uma alteração externa no Grimoire não deve sobrescrever arquivos versionados sem revisão/proveniência correspondente.
