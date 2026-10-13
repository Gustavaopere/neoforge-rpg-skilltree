# VS Sable Hose Connectors

> **Autoridade física atual — 27/09/2026.** Ordem física **#563**: JAR `VS-Sable-HoseConnectors-0.1.8-1.21.1.jar`, mod id `vsfluidlink`, runtime `0.1.8`, SHA-1 `39672703f570bca7290b75148c4091f9299fcfdf`.
- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1

## Propriedades do banco

- **Mod:** VS Sable Hose Connectors
- **Arquivo JAR:** `VS-Sable-HoseConnectors-0.1.8-1.21.1.jar`
- **Versão 1.21.1:** 0.1.8
- **Estado no pack:** Integrado ao Github
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Compat, Tecnologia, Automação
- **Função:** Bridge cross-space para ships/sublevels VS/Sable: transfere fluids, items, rotational power e energy entre estrutura móvel e mundo estático por connectors manuais/magnéticos.
- **Dependências:** Create 6.0.10 e stack Valkyrien Skies ou Sable/Aeronautics conforme uso. Pack atual usa Sable/Aeronautics; a build 0.1.8 precisa de runtime smoke contra versões atuais.
- **Sobreposição:** Potencial overlap com outras bridges Sable/Aeronautics deve ser comparado por tipo de recurso/endpoint; não assumir duplicação só por conectarem estruturas móveis.
- **Compatibilidade/Riscos:** Riscos: dupe/loss em transferência interrompida, stale endpoints após assembly/chunk unload, capability mismatch, kinetic double-source e drift do stack Sable/Aeronautics. Item connectors documentam 32 RPM no source side; não criar buffer/authority paralelos.
- **Observações:** Mod id `vsfluidlink`, runtime 0.1.8. 0.1.8 suporta rotação de Hose/Electric Wire Connectors. Bridge não é storage/energy/kinetic provider; endpoints reais conservam authority.
- **Procedência:** modlist.txt física atual consultada em 13/09/2026 + CurseForge oficial VS / Sable Hose Connectors revalidado em 13/09/2026: 0.1.8 continua a latest release NeoForge 1.21.1 + stack físico Create 6.0.10, Aeronautics 1.3.2 e Sable 2.0.5. Nenhum transfer, reconnect, rotation ou atomicity test foi executado.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/vs-hose-connectors
- **Atualização/Status:** PADRÃO ALEX'S MOBS REVALIDADO EM 13/09/2026 — VS Sable Hose Connectors 0.1.8 permanece exatamente instalado e continua a latest release NeoForge 1.21.1; fluids/items/energy/rotation, magnet connectors, lifecycle e exactly-once preservados.
- **Histórico da decisão:** 
- **Data da última decisão:** 2026-09-09

# Dossiê operacional — padrão Alex's Mobs

> 🔌 **ESCOPO CANÔNICO.** Runtime físico: `VS-Sable-HoseConnectors-0.1.8-1.21.1.jar`, mod id `vsfluidlink`, versão `0.1.8`. O mod é uma **bridge de transferência através da fronteira mundo ↔ ship/sublevel** para Valkyrien Skies e Sable/Aeronautics. Não cria storage, energia ou cinética próprios; conecta providers existentes.
## 1. Função e authority
O projeto permite transferir recursos entre estruturas físicas móveis e infraestrutura estática. A documentação oficial da linha atual cobre **fluids, items, rotational power e energy** quando o respectivo stack físico está presente.
Authorities permanecem:
- inventory provider para items;
- fluid handler/tank provider para fluids;
- Create/kinetic provider para rotation;
- energy capability/provider para energia;
- Sable/Aeronautics/VS para transforms e identidade da estrutura móvel.
`vsfluidlink` apenas materializa a conexão cross-space.
## 2. Hose Connector — fluidos
O **Hose Connector** cria ligação de fluidos entre endpoints compatíveis. A conexão manual é feita com Create wrench conforme a documentação do projeto e interage com Create pipes.
O **Magnet Hose Connector** é a variante de auto-conexão por facing; redstone pode desabilitar seu comportamento automático.
Não tratar o hose como tanque: capacidade, conteúdo e regras de inserção/extração continuam nos fluid handlers conectados.
## 3. Item Hose Connector
O **Item Hose Connector** transfere items entre lados conectados. A documentação da build descreve alimentação cinética mínima de **32 RPM** pelo lado-fonte para a operação dos conectores de item.
Boundary importante:
- o conector-fonte recebe a rotação;
- a ponte não deve criar cópia intermediária persistente dos itens;
- falha/desconexão precisa interromper transferência sem dupe/loss.
A variante **Item Magnet Hose Connector** automatiza a associação por facing.
## 4. Electric Wire Connector
O **Electric Wire Connector** cria vínculo de energia entre endpoints em espaços físicos diferentes.
A variante **Electric Magnet Wire Connector** automatiza conexão por facing.
A bridge não define uma nova unidade de energia. Conversão/compatibilidade precisa respeitar a capability/provider energético real do stack conectado.
## 5. Chain Connector — rotação
O **Chain Connector** transfere força rotacional; sua variante magnetizada faz auto-conexão.
Create continua authority da cinética. Não manter RPM/stress paralelo nem emitir geração de potência apenas porque existe uma conexão cross-space.
## 6. Rotação dos conectores
A linha 0.1.8 adiciona/suporta rotação de Hose Connectors e Electric Wire Connectors, relevante para layout de estruturas móveis.
Após rotation/assembly, transforms dos endpoints precisam continuar corretas e a conexão não pode ficar referenciando coordenadas antigas do main level.
## 7. Stack físico atual e drift de versão
A documentação pública do projeto lista combinações testadas historicamente com Create 6.0.10 e versões mais antigas do Aeronautics/Sable. O pack atual usa **Create 6.0.10** e um stack Aeronautics/Sable mais novo.
A release 0.1.8 é a build atual instalada, mas “release atual” não prova que todo novo stack físico foi runtime-tested pelo autor. Exigir smoke real no ambiente do pack.
## 8. Boundaries de integração própria
- Não duplicar transferência em listener externo.
- Não espelhar inventories/tanks/energy buffers para “garantir” conexão.
- Não reconstruir transforms por heurística se Sable/VS expõe a identidade correta.
- Não conceder Mastery por throughput contínuo, RPM por tick ou item/fluid transferido autonomamente.
- Se endpoint/provider estiver ausente/inválido, a bridge deve desconectar/falhar fechada sem criar ou destruir recurso.
## 9. Sobreposição com outras bridges
O pack contém outras integrações Sable/Aeronautics. Overlap deve ser comparado pelo **tipo de recurso e endpoint**:
- Aero Cables/bridges elétricas podem cruzar energia;
- fluid links podem cruzar líquidos;
- interfaces Create podem cruzar items/fluids por outros mecanismos.
Não declarar duplicata apenas porque dois mods “conectam contraptions”. Testar ownership, alcance, auto-connect e recurso suportado.
## 10. Client / server e multiplayer
Transferência de resource e estado de conexão precisam ser server-authoritative. Cliente pode renderizar connector/link.
Em multiplayer, dois observers não podem provocar transfer duplicado; reconnect do piloto/player não deve recriar conexão lógica em duplicidade.
## 11. Lifecycle crítico
- place/remove connector;
- manual connect/disconnect com wrench;
- magnet auto-connect;
- redstone disable/enable;
- assembly/disassembly;
- rotation da estrutura;
- chunk/sublevel unload;
- server restart;
- endpoint quebrado durante transferência;
- dois endpoints candidatos próximos;
- dimension/space transition quando suportada pelo stack.
## 12. Riscos
1. **Dupe/loss:** transferência interrompida entre simulate/execute phases.
2. **Stale endpoint:** transform/UUID inválido após assembly ou chunk unload.
3. **Cross-capability mismatch:** handler nega insert/extract após bridge prever operação.
4. **Kinetic double-source:** rotação aplicada aos dois lados ou recriada erroneamente.
5. **Energy semantics:** capability/provider externo com limites diferentes.
6. **Magnet ambiguity:** múltiplos candidatos/facing incorreto.
7. **Version drift:** Aeronautics/Sable atual mais novo que combinações publicamente testadas.
## 13. Matriz de testes
- [ ] Dedicated server inicia com Hose Connectors 0.1.8 + Create 6.0.10 + stack Sable/Aeronautics atual.
- [ ] Fluid manual connector transfere exatamente o volume debitado/creditado.
- [ ] Magnet fluid connector conecta/desconecta por facing/redstone.
- [ ] Item connector exige a rotação documentada e não duplica stacks.
- [ ] Item magnet reconnect não repete a última transferência.
- [ ] Electric connector preserva saldo de energia conforme provider.
- [ ] Chain connector transmite rotação sem gerar stress/energia extra.
- [ ] Rotacionar connector/contraption mantém endpoint correto.
- [ ] Assemble/move/disassemble preserva links sem stale references.
- [ ] Chunk unload/reload e server restart não duplicam conexão.
- [ ] Break de endpoint durante transferência falha atomicamente.
Nenhum teste foi marcado como aprovado nesta auditoria documental.
## 14. Evidências
- **Modlist física 08/09/2026:** `VS-Sable-HoseConnectors-0.1.8-1.21.1.jar`, mod id `vsfluidlink`, runtime 0.1.8, mixin `vsfluidlink.mixins.json`; Create 6.0.10 presente.
- CurseForge oficial VS Hose Connectors: fluids/items/rotation/energy, connectors manuais/magnéticos e suporte VS/Sable.
- Guia Tecnologia: papel cross-sublevel e rotação dos connectors na 0.1.8.
## 15. Limitação
A combinação exata 0.1.8 com todas as versões atuais de Aeronautics/Sable do pack ainda não foi executada. Throughput/ranges/configs não foram inferidos além do requisito publicado de 32 RPM para item connectors.
## 16. Revalidação física — 11/09/2026
A modlist física mantém `VS-Sable-HoseConnectors-0.1.8-1.21.1.jar`, mod id `vsfluidlink`, versão `0.1.8`. A file list oficial continua apontando 0.1.8 como a latest release NeoForge 1.21.1.
O stack físico relevante permanece Create `6.0.10`, Create Aeronautics `1.3.2` e Sable `2.0.5`. O papel de bridge — sem ownership próprio de inventory/fluid/energy/kinetics — continua válido. O estado **Integrado ao Github** e a decisão **Sem decisão** foram preservados. Nenhum transfer, reconnect, rotation, chunk reload ou atomicity test foi executado nesta recatalogação.
## 17. Revalidação física e upstream — 13/09/2026
O runtime físico permanece `VS-Sable-HoseConnectors-0.1.8-1.21.1.jar`, mod id `vsfluidlink`, versão `0.1.8`, e a file list oficial continua tratando 0.1.8 como a release NeoForge 1.21.1 atual. Create `6.0.10`, Create Aeronautics `1.3.2` e Sable `2.0.5` permanecem o stack relevante. O boundary de bridge continua intacto: inventory/fluid/energy/kinetics pertencem aos endpoints/providers reais; o addon apenas cruza a fronteira de espaço físico. Nenhum transfer, reconnect, rotation, chunk reload ou atomicity test foi executado nesta revalidação.

## Atualização upstream e gate de manutenção — 11/10/2026

**Estado upstream:** CurseForge continua publicando `VS-Sable-HoseConnectors-0.1.8-1.21.1.jar` (NeoForge 1.21.1, 28/06/2026); coincide com a versão da última snapshot física documentada. **Decisão:** manter versão; não confundir checagem upstream com nova inspeção do JAR. Regressões prioritárias em Sable/Aeronautics: endpoint cross-space, auto-connect magnético, transferências exatamente uma vez, 32 RPM para item connectors, assembly, unload/reload e restart. **Fonte:** https://www.curseforge.com/minecraft/mc-mods/vs-hose-connectors/files/all?version=1.21.1

> **Autoridade e limites:** revisão documental baseada no último inventário físico histórico, sem novo dump de JARs/SHA e sem testes in-game nesta execução. Não alterar versão instalada, filename ou modlist sem evidência física atual.

## Reauditoria de publicação e compatibilidade — 13/10/2026

**Projeto:** VS / Sable Hose Connectors. **Última release pública identificada para Minecraft 1.21.1 / NeoForge:** `0.2.2` (`VS-Sable-HoseConnectors-0.2.2-1.21.1.jar`). **Referência oficial:** https://www.curseforge.com/minecraft/mc-mods/vs-hose-connectors/files/9147235.

**Novidade confirmada pelo changelog de 0.2.2 (12/10/2026):** conexão entre hoses por clique direito SEM wrench; com wrench, clique direito gira o connector sem sneak, e sneak+clique direito recolhe o connector. Duas opções permitem recuperar a interação antiga: `Connect hoses by right-clicking without a wrench` (padrão ON) e `Wrench right-click behavior` (`Connect hoses` ou `Rotate`, padrão Rotate). Isso modifica interação do jogador, workflows de build de ship e event handling; não indica mudança na ownership de líquidos/itens/energia/rotação. **QA:** matriz com/sem wrench, sneak, config antiga/nova, canal de magnet connector, links em ship após disassembly/chunk unload, transfer exactly-once e servidor multiplayer; checar datapacks/controles que usem clique.

**Autoridade física:** permanece o JAR/versão registrados nas seções históricas deste dossiê e na `modlist(1).txt`; NÃO foi disponibilizada nova lista física nem realizada instalação/teste in-game. **Recomendação:** atualização física pendente de reconciliação de dependências, SHA e validações; não interpretar o número publicado como versão instalada. **Filename `.md`:** ✅ herdado e versão do JAR físico preservados conforme protocolo canônico.
