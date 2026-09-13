# Create: Power Grid

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db81d898d8ea87618ea7e5
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist 08.09.2026.txt` / `modlist.txt` — JAR confirmado fisicamente
- **Data da exportação:** 2026-09-10

## Propriedades do banco

- **Mod:** Create: Power Grid
- **Arquivo JAR:** `powergrid-mc1.21.1-0.6.1.jar`
- **Versão 1.21.1:** 0.6.1
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Tecnologia, Automação
- **Função:** Sistema de eletricidade inspirado em engenharia real para o ecossistema Create, com geração, distribuição, circuitos e componentes elétricos.
- **Dependências:** Create/NeoForge 1.21.1 conforme projeto. JAR embute EJML 0.44.0 e Sable Companion 1.6.0 sob o host.
- **Sobreposição:** Sobreposição parcial com outros sistemas elétricos/industriais, mas Power Grid possui modelo próprio de tensão/circuitos/dispositivos; não é substituto automático de Crafts & Additions/TFMG/Oritech.
- **Compatibilidade/Riscos:** Riscos: sobrecarga/voltagem, loops FE, TFMG integration drift, Sable fast-contraption regression, state persistence, redes grandes e convergência entre Sable Companion embedded 1.6.0 e outras versões internas. Nenhum conflito embedded foi assumido sem teste.
- **Observações:** Runtime 0.6.1, Release 26/08/2026. Changelog 0.6.1 inclui fixes de custom expression, switch state, TFMG Community Edition, hanging wires e crash em contraptions Sable rápidas.
- **Procedência:** modlist.txt física canônica atual de 10/09/2026 + CurseForge oficial Create: Power Grid 0.6.1 + hierarquia física JarJar.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/power-grid
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 10/09/2026 — Create: Power Grid 0.6.1 reconstruído: grid/circuit model, FE bridge, devices, TFMG/Sable fixes, embedded EJML/Sable Companion, lifecycle, riscos e testes.
- **Histórico da decisão:** A revisão sugeriu remover Create: Power Grid para simplificar a infraestrutura energética ao redor de Create/TFMG. O usuário posteriormente informou que manteve alguns addons Create sugeridos porque gosta deles. Confirmado carregado como 0.6.0.1 em 22/08/2026; sem decisão final individual.
- **Data da última decisão:** 2026-09-10

> 🔎 **ESCOPO CANÔNICO.** Runtime físico: `powergrid-mc1.21.1-0.6.1.jar`, mod id `powergrid`, versão `0.6.1`, NeoForge 1.21.1. Create: Power Grid adiciona um sistema elétrico próprio ao ecossistema Create, com redes/circuitos modelados por princípios elétricos, geração, cargas e dispositivos. O JAR embute EJML 0.44.0 e Sable Companion 1.6.0; esses artefatos permanecem sob o host.

## 1. Identidade e papel
- **Mod:** Create: Power Grid.
- **JAR:** `powergrid-mc1.21.1-0.6.1.jar`.
- **Mod id:** `powergrid`.
- **Runtime:** `0.6.1`.
- **Loader/jogo:** NeoForge 1.21.1.
- **Ambiente:** Client & Server.
- **Licença:** Apache 2.0.
- **Papel:** eletricidade, circuitos e dispositivos elétricos integrados à estética/automação Create.
- **Decisão:** Sem decisão.

## 2. Modelo elétrico
O projeto descreve uma rede baseada em princípios como Lei de Ohm e regras de junção, com circuitos que precisam formar caminhos válidos para corrente. Produção e demanda afetam tensão, e desequilíbrio pode causar mau funcionamento/dano.
Esse modelo não deve ser reduzido a “FE com fios”: a simulação e os dispositivos têm regras próprias.

## 3. Produção, distribuição e perdas
Há geração elétrica e transporte de potência, com vantagem de tensão maior para transferir mais energia com perdas menores. Testar redes pequenas e grandes, carga variável, circuito aberto/fechado e mudanças durante tick.

## 4. Dispositivos publicados
A linha inclui dispositivos como generators/solar panels, Electric Motor, Servo, Basin Heater, Growth Lamp, Light Bulb/Factory Lights, Alarm Bell, Redstone Converter, Portable Drill/Saw, Electro-Zapper e Modular Displays.
Cada dispositivo deve ser testado pela função concreta; presença na API/conteúdo não prova que esteja incorporado à progressão do pack.

## 5. Circuit Design Table
O projeto permite criar circuit boards funcionais/customizáveis em uma Circuit Design Table. Essa superfície combina dados/configuração e execução runtime; validar recipe/projeto, persistência do board e comportamento após reload/restart.

## 6. Compatibilidade FE
Power Grid publica conectores/dispositivos para interação com Forge Energy, incluindo inverter/conectores. Isso é uma ponte de energia, não equivalência de modelo.
Testar conversão, limites de transferência, direção, perda e prevenção de loops de conversão/energia infinita.

## 7. Release 0.6.1
A build instalada é Release oficial 1.21.1. O changelog inclui, entre outros:
- fix de parsing de custom expressions;
- restore correto de switch state ao carregar mundo;
- correções de hanging wire e loot tables;
- correção de crash com TFMG Community Edition e aviso de que TFMG regular não é mais suportado nessa superfície;
- correção de crash com contraptions Sable em alta velocidade;
- fixes de integração CC e opções para displays.
Esses pontos são regression gates concretos.

## 8. Sable e dependência embedded
O JAR físico embute `sable-companion-common-1.21.1-1.6.0.jar` e módulos EJML 0.44.0. Eles não recebem ordinal próprio.
Outros hosts do pack embutem versões diferentes de Sable Companion, inclusive 1.5.0. Isso cria risco de resolução/version convergence, mas **não é prova de conflito runtime** sem observar a resolução efetiva do loader e executar smoke tests.

## 9. TFMG e stack energético
O pack possui múltiplos sistemas energéticos/industriais. Sobreposição com Crafts & Additions, TFMG, Oritech e outros é parcial: máquinas, tensão, circuitos e integrações não são intercambiáveis.
A nota 0.6.1 sobre TFMG deve ser tratada exatamente: fix para TFMG Community Edition e ausência de suporte ao TFMG regular naquela integração, não “incompatibilidade total” com qualquer coexistência.

## 10. Client/server e persistência
Rede elétrica, estados de switch, carga e dano precisam ser server-authoritative. Persistência crítica:
- switch state;
- circuit boards;
- rede após chunk unload;
- contraptions Sable;
- FE bridge;
- reconnect/restart.

## 11. Performance
Análise de rede/circuito pode crescer com quantidade de nós. EJML embedded sugere uso de rotinas matemáticas pelo host, mas não autoriza inferir algoritmo específico sem source pinado. Benchmarkar grids representativas, especialmente após reconfiguração frequente.

## 12. Riscos
1. Sobrecarga/voltagem e dano inesperado por rede mal balanceada.
2. Loops de conversão FE/Power Grid.
3. TFMG integration drift.
4. Sable fast-contraption regression.
5. Embedded Sable Companion version convergence.
6. Switch/circuit state perdido após world load.
7. Custom expression inválida.
8. Custo de redes grandes.
9. Sobreposição curatorial com outros sistemas elétricos.

## 13. Matriz de testes
- [ ] Dedicated server e cliente iniciam com 0.6.1.
- [ ] Circuito simples respeita caminho fechado e carga.
- [ ] Rede com geração/demanda variável mantém valores coerentes.
- [ ] Switch state persiste após save/restart.
- [ ] FE connector/inverter transfere sem loop/dupe.
- [ ] Electric Motor/Basin Heater e um consumer representativo funcionam.
- [ ] Custom circuit board persiste após reload/restart.
- [ ] Coexistência com stack TFMG atual não reproduz crash.
- [ ] Contraption Sable rápida não reproduz crash corrigido.
- [ ] Sable Companion embedded resolve sem conflito observável com outros hosts.
Nenhum teste foi marcado como aprovado nesta auditoria documental.

## 14. Evidências e limites
- Modlist física: JAR, mod id/runtime, três mixin configs, EJML 0.44.0 e Sable Companion 1.6.0 embedded.
- CurseForge oficial: Release 0.6.1 NeoForge 1.21.1 de 26/08/2026, Client & Server.
- Descrição/changelog oficiais: modelo elétrico, dispositivos, FE compat e fixes 0.6.1.
- **Limite:** config local, topologia real das redes e resolução efetiva entre versões embedded de Sable Companion não foram testadas; não foi declarado conflito sem evidência.