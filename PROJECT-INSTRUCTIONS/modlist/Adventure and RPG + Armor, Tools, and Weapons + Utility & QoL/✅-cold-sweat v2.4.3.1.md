# Cold Sweat

> **Reauditoria física — 17/09/2026.** Versão catalogada atual: `2.4.3.1`. O conteúdo abaixo foi reconstruído a partir da página Notion reconciliada e da autoridade física atual; a URL da própria página Notion foi deliberadamente omitida.

## Propriedades do registro

- **Mod:** Cold Sweat
- **Arquivo JAR:** ColdSweat-2.4.3.1.jar
- **Versão 1.21.1:** 2.4.3.1
- **Categoria:** Clima
- **Função:** Sistema principal de temperatura corporal do pack: calcula conforto térmico a partir de ambiente e estado do jogador, oferece insulation e dispositivos/itens térmicos como Hearth, Boiler, Icebox, Waterskin e Thermometer.
- **Dependências:** Provider térmico principal. Create: Cold Sweat e Thirst Was Reclaimed permanecem integrations relevantes; a modlist física atual contém Create 6.0.10 e Thirst Was Reclaimed 3.0.5. Suporte upstream antigo ao external heater do Immersive Engineering não implica integração ativa se IE estiver ausente.
- **Estado no pack:** Integrado ao Github
- **Estado da pesquisa:** Verificado
- **Compatibilidade/Riscos:** Authority de temperatura corporal; evitar double-application por outros providers. Riscos em insulation, block temperature emitters, dimension/death lifecycle, Create/Sable moving objects, KubeJS configs e integrations. 2.4.3 altera block-temperature internals e corrige Create tanks/pipes, Sable contraptions, build-limit e outros casos; 2.4.3.1 corrige crash sem Create.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/cold-sweat
- **Procedência:** modlist física de 16/09/2026 + CurseForge/changelog oficial Cold Sweat 2.4.3 e 2.4.3.1 + dossiê Notion anterior 2.4.2. Nenhum runtime test executado.
- **Observações:** mod id `cold_sweat`; runtime 2.4.3.1. O JAR 2.4.2 anteriormente auditado embarcava Sable Companion 1.4.2; o conteúdo jar-in-jar interno do novo JAR 2.4.3.1 não foi re-inspecionado nesta passagem, portanto essa versão interna não é promovida como fato atual.
- **Atualização/Status:** READITADO EM 17/09/2026 — runtime físico atualizado de 2.4.2 para 2.4.3.1. Deltas 2.4.3 e hotfix 2.4.3.1 incorporados; decisão `Manter` preservada.
- **Decisão:** Manter
- **Histórico da decisão:** Mantido formalmente em 22/08/2026 como sistema principal de temperatura corporal. O compat antigo Cold Sweat and Aeronautics foi removido, mas Cold Sweat permanece provider térmico principal. Em 08/09/2026, a decisão foi preservada e a ficha reconciliada à build física 2.4.2 atual.
- **Sobreposição:** Cold Sweat controla temperatura corporal. Estações, clima visual, chuva/neve e outros efeitos ambientais podem coexistir, mas não devem liquidar uma segunda temperatura corporal sem integração explícita.
- **Data da última decisão:** 2026-08-22

# Dossiê operacional — padrão Alex's Mobs
> 🌡️ Versão física confirmada: `ColdSweat-2.4.3.1.jar`, mod id `cold_sweat`, runtime `2.4.3.1`, NeoForge 1.21.1. No pack, Cold Sweat é o **provider principal de temperatura corporal** e possui decisão formal `Manter` desde 22/08/2026.
## 1. Papel e authority térmica
Cold Sweat calcula o estado térmico do jogador a partir do ambiente e de fatores de gameplay. Outros mods podem fornecer clima, estações, chuva, roupas ou fontes de calor, mas a temperatura corporal final não deve ser liquidada por dois sistemas paralelos sem bridge explícita.
## 2. Fontes ambientais
A documentação do projeto cobre influência de biomas/clima, blocos e outras condições ambientais. Integrações próprias devem observar os valores/configs do provider em vez de duplicar uma fórmula externa de “frio/calor”.
## 3. Insulation
Armaduras e itens podem receber insulation. Essa camada modifica a resposta térmica do jogador; não deve ser traduzida automaticamente em armor toughness, resistência elemental ou stamina sem regra explícita do modpack.
A 2.4.2 corrige casos de configs de insulators via KubeJS, tornando essa superfície um regression gate.
## 4. Hearth, Boiler e Icebox
Cold Sweat possui infraestrutura térmica como **Hearth, Boiler e Icebox**. Esses blocos controlam efeitos térmicos do provider e combustível/estado correspondente.
A 2.4.2 adiciona integração com o **external heater do Immersive Engineering** para boilers/hearths. Energy/heat input do IE não deve ser debitado duas vezes por scripts externos.
## 5. Waterskin e Thermometer
Waterskins e instrumentos como Thermometer fazem parte do ecossistema de sobrevivência térmica. UI/tooltip apenas apresenta estado; autoridade de temperatura e consumo/efeito dos itens permanece no servidor/provider.
## 6. Create/Sable/Aeronautics
A 2.4.2 corrige temperatura não emitida por blocos que fazem parte de objetos **Sable/Create: Aeronautics**. Isso prova uma integração concreta com objetos móveis/contraptions.
Teste obrigatório: fonte térmica em estrutura móvel deve influenciar o ambiente exatamente como a implementação define, sem emitir duas vezes quando estacionária/móvel.
O JAR físico de Cold Sweat 2.4.2 também embarca `/META-INF/jarjar/sable-companion-common-1.21.1-1.4.2.jar`, mod id `sablecompanion`, versão `1.4.2`. Pela regra canônica de inventário, essa cópia é **jar-in-jar do host Cold Sweat** e não recebe página top-level própria. A metadata física confirma presença e versão; esta ficha não inventa funções adicionais do componente além do contract demonstrado pelas fontes.
## 7. Sede
A release atual troca o alvo da integração antiga **Thirst Was Taken** para **Thirst Was Reclaimed**. Isso é compat específica; não assumir que qualquer mod de thirst recebe o mesmo contract.
## 8. Configuração e datapacks
O projeto oferece configuração extensa e suporta dados/configs customizáveis, inclusive KubeJS em superfícies documentadas. Config do servidor é authority para gameplay; tooltip/menu cliente não pode sobrepor valores server-side.
Qualquer ajuste de bioma, insulation ou emitter deve ser versionado junto do pack para evitar mundos/clientes divergentes.
## 9. Death, respawn e dimension lifecycle
A linha recente de Cold Sweat corrigiu state térmico persistindo/congelando após morte ou troca de dimensão em versões suportadas. Mesmo quando a nota não se aplica igualmente a todos patch levels, death/clone/dimension transition permanecem gates obrigatórios.
O jogador deve reconstruir temperatura a partir do state real, sem carregar cache térmico inválido de outro ambiente.
## 10. Client/server e multiplayer
Temperatura corporal, efeitos, insulation e block emitters que afetam gameplay são server-authoritative. HUD, tooltips, partículas e feedback são cliente.
Em multiplayer, cada jogador deve receber state térmico próprio; fontes compartilhadas não podem vazar cache/temperatura entre players.
## 11. Relação com o pack
- **Create: Cold Sweat**: bridge específica instalada no ecossistema atual.
- **Create/Aeronautics/Sable**: objetos móveis permanecem regression surface; a 2.4.3 corrige novos casos de block temperature em Sable contraptions e Create tanks/pipes.
- **Immersive Engineering**: a 2.4.2 possui suporte upstream ao external heater para Boiler/Hearth, mas Immersive Engineering está **ausente** do snapshot físico atual; a integração permanece dormente nesta instância.
- **Thirst Was Reclaimed**: alvo de compat da release 2.4.2.
- Mods de estações/clima: inputs ambientais possíveis, não second authority de body temperature.
## 12. Riscos
1. Double temperature settlement por outro provider.
2. Insulation aplicada duas vezes por armor/compat scripts.
3. Emitter móvel Sable/Create deixar cache duplicado.
4. KubeJS insulator config divergente após reload.
5. Death/dimension manter state térmico stale.
6. Thirst integration apontar para provider antigo.
7. Se Immersive Engineering voltar ao pack, external heater consumir/creditar energia/heat duas vezes por integração duplicada.
8. Client HUD divergir do state server-side.
## 13. Matriz de testes
1. Dedicated server boot com Cold Sweat 2.4.3.1.
2. Transição entre biomas/altitudes/ambientes quentes e frios.
3. Equip/unequip de insulation e reload de config.
4. Hearth, Boiler e Icebox com combustíveis/estado real.
5. Se Immersive Engineering voltar ao pack: external heater em Boiler/Hearth — capability upstream 2.4.2 atualmente dormente.
6. Fonte térmica dentro de objeto Sable/Aeronautics — regressão 2.4.2.
7. KubeJS insulator config — regressão 2.4.2.
8. Death/respawn e dimension travel.
9. Multiplayer com dois jogadores em condições térmicas diferentes.
10. Thirst Was Reclaimed + Create: Cold Sweat sem double effect.
## 14. Evidência
- modlist física de 16/09/2026: `ColdSweat-2.4.3.1.jar` / runtime 2.4.3.1;
- inventário físico do host: `sable-companion-common-1.21.1-1.4.2.jar` embarcado via jar-in-jar;
- CurseForge oficial das releases 2.4.2, 2.4.3 e 2.4.3.1;
- changelog: IE external heater, Thirst Was Reclaimed, Sable temperature fix, KubeJS insulator fix e tooltip fix;
- decisão histórica do catálogo: `Manter` como provider térmico principal.
> 🔥 Authority canônica: **Cold Sweat decide a temperatura corporal**. Outros sistemas ambientais devem alimentar/integrar esse provider, não criar um segundo saldo térmico concorrente.
## 15. Atualização instalada — 2.4.3
O changelog oficial 2.4.3 registra:
- overhaul do algoritmo de propagação do **Hearth**, distribuindo melhor a área de efeito, especialmente via pipes;
- horários configuráveis de hottest/coldest time em `world.toml`;
- remoção de compat built-in com Aquamirae e Boatload;
- melhoria de performance quando múltiplos block-temperature calculations ocorrem no mesmo tick;
- fixes de advancements em 1.21, temperatura no build limit, block temperature em Sable contraptions, Create fluid tanks/pipes com maximum temperature ilimitada, Hearth top-half em Create train, crash de armor sets no creative menu, item requirements de insulation, partículas da Icebox e sincronização de filled Waterskins;
- migração técnica de campos de `BlockTemp` para métodos context-aware que recebem world/position/blockstate, mantendo os métodos antigos deprecated/delegando por compatibilidade.
Essas mudanças tornam Hearth/pipes, Sable/Create contraptions, insulation requirements, Waterskin sync e addons que acessam BlockTemp diretamente regression gates explícitos.
## 16. Hotfix 2.4.3.1
A build física `2.4.3.1`, publicada em 15/09/2026 para NeoForge 1.21.1, corrige **crash ao carregar o jogo sem Create instalado**. O pack atual possui Create 6.0.10, mas a correção continua relevante para robustez da dependência opcional e perfis de teste.
Nenhum teste térmico, Create/Sable, death/dimension, KubeJS ou multiplayer foi executado nesta atualização documental.
### Regression gates da 2.4.3/2.4.3.1
- [ ] Dedicated server inicia com Cold Sweat 2.4.3.1.
- [ ] Hearth/Boiler/Icebox e Waterskin permanecem sincronizados.
- [ ] Block temperature funciona em Sable/Create: Aeronautics contraptions.
- [ ] Create tanks/pipes não recebem maximum temperature ilimitada.
- [ ] Cold Sweat inicia sem Create instalado em perfil isolado, validando o hotfix 2.4.3.1.
