# Tom's Simple Storage Mod

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db816fbc2bd8ce2464d9f5
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist.txt` — 595 mods top-level
- **Data da exportação:** 2026-09-09

## Propriedades do banco

- **Mod:** Tom's Simple Storage Mod
- **Arquivo JAR:** `toms_storage-1.21-2.4.2.jar`
- **Versão 1.21.1:** `2.4.2`
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 09/09/2026 — Tom's Simple Storage 2.4.2; rede, terminals, wireless, filters, Open Crate, Create Contraption Terminals, lifecycle, riscos e testes catalogados.
- **Categoria:** Armazenamento; Tecnologia; QoL
- **Compatibilidade/Riscos:** Ghost inventory/UI stale, capability mismatch com inventories modded, loops de transferência, chunk boundaries, wireless permissions/range e lifecycle de contraptions. Crafting Terminal é crafting manual integrado; não documentar como autocrafting industrial autônomo.
- **Decisão:** Sem decisão
- **Dependências:** NeoForge 1.21.1. Create Contraption Terminals 1.4.0 está presente e habilita terminais em contraptions Create. Sophisticated Storage é provider local complementar. AE2 não está presente na modlist física atual.
- **Estado da pesquisa:** Verificado
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/toms-storage
- **Função:** Provider de rede simples de inventários físicos com Storage/Crafting Terminal, Inventory Connector, Open Crate, Wireless Terminal, filtros e Inventory Hopper; agrega containers sem convertê-los em storage digital abstrato.
- **Histórico da decisão:** vazio
- **Observações:** mod id `toms_storage`; runtime 2.4.2. Create Contraption Terminals 1.4.0 está fisicamente presente. Config/regras efetivas de wireless não foram lidas. Não inferir autocrafting automático do Crafting Terminal.
- **Procedência:** Runtime/JAR e stack: modlist física canônica 08/09/2026. Features e Create Contraption Terminals: páginas oficiais Tom's Simple Storage. Contratos adicionais: source oficial linha 1.21, sem commit pin do binário 2.4.2.
- **Sobreposição:** Sobreposição parcial com Sophisticated Storage no domínio de armazenamento; no pack atual Tom's pode atuar como camada de agregação/acesso sobre inventories físicos. AE2 foi removido e não deve mais aparecer como concorrente ativo.
- **Data da última decisão:** 2026-09-09

> 📦 **ESCOPO CANÔNICO.** Runtime físico: `toms_storage-1.21-2.4.2.jar`, mod id `toms_storage`, versão `2.4.2`. Tom's Simple Storage é o **provider de rede simples de inventários físicos** do pack: agrega containers conectados e os expõe por terminais, conectores, filtros, acesso sem fio e crafting manual integrado. Não confundir o Crafting Terminal com um sistema de autocrafting industrial autônomo.

## 1. Identidade, versão e papel
- **Mod:** Tom's Simple Storage Mod.
- **JAR físico:** `toms_storage-1.21-2.4.2.jar`.
- **Mod id:** `toms_storage`.
- **Versão:** `2.4.2`.
- **Loader/jogo:** NeoForge 1.21.1.
- **Ambiente:** Client & Server.
- **Decisão vigente:** Sem decisão; preservada.
- **Papel no pack atual:** storage centralizado simples, sem AE2 presente na modlist física.

## 2. Authority e ownership
Tom's é authority da **topologia de sua rede**, descoberta dos inventários conectados, filtros e acesso pelos próprios terminais. Os itens continuam fisicamente armazenados nos inventories/providers conectados; Tom's não transforma todos os itens em um armazenamento digital abstrato.

Sophisticated Storage continua authority dos próprios chests/barrels/upgrades. Create continua authority de contraptions. A integração apenas permite que Tom's enxergue/acesse o conteúdo por contratos suportados.

## 3. Componentes principais confirmados
A documentação oficial publica como features centrais:
- **Storage Terminal**;
- **Crafting Terminal**;
- **Inventory Connector**;
- **Open Crate**;
- **Wireless Terminal**;
- **Filtered Connectors**;
- **Inventory Hopper**.

Esses componentes formam uma rede sobre inventories físicos em vez de criar um sistema de drives/cells.

## 4. Inventory Connector e rede
O Inventory Connector conecta inventories adjacentes à rede. Cabos/trims e conectores estendem a topologia conforme o bloco utilizado.

O source 1.21 confirma que connectors podem ser configurados/filtrados com o Inventory Configurator. O filtro é parte da regra de exposição/roteamento e deve ser validado com inventories modded para evitar itens invisíveis ou acesso indevido.

## 5. Storage Terminal
O Storage Terminal apresenta de forma agregada o conteúdo dos inventories acessíveis pela rede. A UI deve refletir o state real do servidor:
- retirar item precisa debitar exatamente um source inventory;
- inserir precisa escolher um destino válido;
- filtros/restrições do container de origem continuam relevantes;
- mudanças simultâneas por hopper/player/automation precisam atualizar a visão sem ghost stacks.

## 6. Crafting Terminal
O Crafting Terminal combina acesso ao storage com uma grade de crafting integrada.

**Limite operacional:** isso é crafting interativo/manual a partir dos materiais acessíveis. A presença do terminal não prova que Tom's base tenha scheduling, padrões, máquinas-alvo ou autocrafting automático no estilo de redes industriais. Addons que adicionem automação devem ser catalogados separadamente.

## 7. Wireless Terminal
O Wireless Terminal fornece acesso remoto à rede. O source da linha 1.21 também documenta regras de alcance ligadas ao nível do beacon e variantes de conexão que podem ampliar acesso na mesma dimensão ou entre dimensões.

Como a configuração/build exata não foi inspecionada em source pin, validar no runtime:
- alcance efetivo;
- requisitos de energia/beacon quando aplicáveis;
- troca de dimensão;
- vínculo com a rede correta;
- permissions em multiplayer.

## 8. Open Crate
O Open Crate transforma a interação com o bloco à frente em uma superfície de inventory e pode materializar itens como entidades conforme sua operação.

É um ponto de risco para automação: testar inserção/extração, bloco alvo removido, chunk unload e interação com collectors/hoppers para evitar dupe ou item loss.

## 9. Filtered Connectors e Inventory Hopper
Filtered Connectors restringem quais itens atravessam/aparecem em determinado vínculo. Inventory Hopper adiciona transferência automática dentro do ecossistema Tom's.

Filtros devem ser server-authoritative e persistir após restart. Loops entre Tom's, hoppers vanilla, Create e pipes externos precisam ser stress-tested.

## 10. Create Contraption Terminals
A página oficial de Tom's declara explicitamente que **terminals funcionam em Create contraptions quando Create Contraption Terminals está instalado**.

O pack possui `createcontraptionterminals-1.21-1.4.0.jar`, portanto a integração é concreta. Testar:
- terminal montado em train/contraption;
- assemble/disassemble;
- movimento entre chunks;
- acesso ao inventário correto antes/depois da montagem;
- ausência de duplicação ao mover inventories conectados.

Create Contraption Terminals é a bridge; Tom's base não assume ownership da física/movimento da contraption.

## 11. Sophisticated Storage
Sophisticated Storage está presente e pode fornecer inventories locais com upgrades. Tom's pode ocupar papel de **camada de agregação/acesso**, enquanto Sophisticated permanece provider de storage local.

A coexistência é complementar, mas exige teste de capabilities, upgrades que bloqueiam inserção/extração e mudanças de tamanho/inventory handler.

## 12. Client / server e multiplayer
- **Servidor:** topologia, contents, insertion/extraction, filtros e autorização funcional.
- **Cliente:** terminal UI, busca e feedback.

Em multiplayer, dois jogadores operando o mesmo terminal não podem consumir o mesmo stack duas vezes ou manter ghost state. Network updates devem convergir após transferências concorrentes.

## 13. Lifecycle
Validar:
- world/server boot;
- montar rede e adicionar/remover inventories;
- chunk unload/reload de parte da rede;
- server restart;
- quebra/substituição de connector;
- acesso wireless após relog/dimension change;
- contraption assemble/disassemble;
- container modded mudando capacidade/upgrades;
- dois players + automation concorrente.

## 14. Riscos técnicos
1. **Ghost inventory/UI stale:** mudanças externas podem não aparecer imediatamente.
2. **Capability mismatch:** inventories modded podem expor regras especiais de inserção/extração.
3. **Network loops:** hoppers/automation podem recircular itens.
4. **Chunk boundaries:** parte da rede descarregada pode alterar descoberta/acesso.
5. **Wireless permissions/range:** acesso remoto precisa respeitar regras da rede e servidor.
6. **Contraption lifecycle:** mover terminal/inventories pode criar state stale ou dupe se integração falhar.
7. **Storage overlap:** Tom's e Sophisticated Storage cumprem papéis parcialmente sobrepostos; o valor marginal é decisão de design, não incompatibilidade técnica automática.
8. **Autocrafting ambiguity:** crafting terminal não deve ser confundido com scheduling automático para máquinas Create.

## 15. Matriz de testes
- [ ] Dedicated server inicia com Tom's 2.4.2.
- [ ] Storage Terminal lista exatamente o conteúdo dos inventories conectados.
- [ ] Inserção/extração preserva quantidade e regras do inventory alvo.
- [ ] Crafting Terminal consome ingredientes uma única vez e devolve remainder corretamente.
- [ ] Filtered Connector persiste regras após restart.
- [ ] Inventory Hopper não cria loop/dupe com hopper vanilla/Create.
- [ ] Wireless Terminal respeita alcance/vínculo/dimensão do runtime atual.
- [ ] Chunk unload de parte da rede não corrompe state.
- [ ] Sophisticated Storage com upgrades continua acessível somente conforme capabilities reais.
- [ ] Create Contraption Terminals mantém acesso correto após assemble/move/disassemble.
- [ ] Dois jogadores usando a mesma rede não geram ghost stacks ou dupe.

Nenhum teste foi marcado como aprovado nesta auditoria.

## 16. Evidências
- Modlist física canônica 08/09/2026: JAR/mod id/versão; AE2 ausente; Sophisticated Storage e Create Contraption Terminals presentes.
- CurseForge/Modrinth oficiais Tom's Simple Storage: Storage & Crafting Terminal, Inventory Connector, Open Crate, Wireless Terminal, Filtered Connectors, Inventory Hopper e integração com Create Contraption Terminals.
- Source oficial da linha 1.21: tooltips/contratos de connectors, filters, wireless e crafting terminal; usado como arquitetura, não como commit pin exato do binário 2.4.2.
