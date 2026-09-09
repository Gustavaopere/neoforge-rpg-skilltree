# VS Sable Hose Connectors

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db81f08712c0f420eeb7ad
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist.txt` — 595 mods top-level
- **Data da exportação:** 2026-09-09

## Propriedades do banco

- **Mod:** VS Sable Hose Connectors
- **Arquivo JAR:** `VS-Sable-HoseConnectors-0.1.8-1.21.1.jar`
- **Versão 1.21.1:** `0.1.8`
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 09/09/2026 — dossiê reconstruído; fluids/items/energy/rotation, magnet connectors, lifecycle e exactly-once catalogados.
- **Categoria:** Compat; Tecnologia; Automação
- **Compatibilidade/Riscos:** Riscos: dupe/loss em transferência interrompida, stale endpoints após assembly/chunk unload, capability mismatch, kinetic double-source e drift do stack Sable/Aeronautics. Item connectors documentam 32 RPM no source side; não criar buffer/authority paralelos.
- **Decisão:** Sem decisão
- **Dependências:** Create 6.0.10 e stack Valkyrien Skies ou Sable/Aeronautics conforme uso. Pack atual usa Sable/Aeronautics; a build 0.1.8 precisa de runtime smoke contra versões atuais.
- **Estado da pesquisa:** Verificado
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/vs-hose-connectors
- **Função:** Bridge cross-space para ships/sublevels VS/Sable: transfere fluids, items, rotational power e energy entre estrutura móvel e mundo estático por connectors manuais/magnéticos.
- **Histórico da decisão:** vazio
- **Observações:** Mod id `vsfluidlink`, runtime 0.1.8. 0.1.8 suporta rotação de Hose/Electric Wire Connectors. Bridge não é storage/energy/kinetic provider; endpoints reais conservam authority.
- **Procedência:** modlist.txt física atual de 08/09/2026 + CurseForge oficial VS Hose Connectors 0.1.8 + Guia Tecnologia atual.
- **Sobreposição:** Potencial overlap com outras bridges Sable/Aeronautics deve ser comparado por tipo de recurso/endpoint; não assumir duplicação só por conectarem estruturas móveis.
- **Data da última decisão:** 2026-09-09

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
