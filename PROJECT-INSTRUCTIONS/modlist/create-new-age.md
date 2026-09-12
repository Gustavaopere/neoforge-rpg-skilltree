# Create: New Age

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db814ea295fa99ada3b049
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist 08.09.2026.txt` / `modlist.txt` — JAR confirmado fisicamente
- **Data da exportação:** 2026-09-10

## Propriedades do banco

- **Mod:** Create: New Age
- **Arquivo JAR:** `create-new-age-1.2.0+neoforge-mc1.21.1.jar`
- **Versão 1.21.1:** 1.2.0+mc1.21.1
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Tecnologia, Automação
- **Função:** Addon energético de Create com geração e uso de eletricidade, motores, Energiser, wires, sistema térmico, solar heating e reatores nucleares multibloco integrados a boilers/steam engines do Create.
- **Dependências:** Na linha Minecraft 1.21.1, o projeto declara apenas Create como requisito obrigatório. `esl-neoforge-1.21.1-1.0.3+neoforge-mc1.21.1.jar` está embarcado em `/META-INF/jars/` no JAR físico e não é top-level.
- **Sobreposição:** Pode coexistir com Create: Crafts & Additions; o próprio upstream informa compatibilidade esperada. Sobreposição elétrica/térmica não implica substituição automática: comparar geração, transporte, conversion e progression concretos.
- **Compatibilidade/Riscos:** Sistema próprio de eletricidade/calor/nuclear. Riscos em energia↔rotação, heat transfer, reactor overheat/explosion, wire rendering, contraptions Aeronautics e CC:Tweaked WIP. A 1.2.0 melhora wire rendering e corrige crashes com Create Aeronautics.
- **Observações:** mod id `create_new_age`; runtime 1.2.0+mc1.21.1. Features oficiais: coils/magnets para geração elétrica, motors, Energiser, wires, nuclear reactors, solar panels e heat system. 1.2.0 adiciona Street Lights/Lamp Posts, radiation/Geiger counter, reactor explosion on overheat e CC:Tweaked compatibility WIP.
- **Procedência:** Modlist física canônica de 08/09/2026 + runtime `create_new_age` 1.2.0+mc1.21.1 + Modrinth/CurseForge oficiais da release NeoForge 1.2.0 e documentação do projeto.
- **Fonte:** https://modrinth.com/mod/create-new-age/version/1.2.0%2Bmc1.21.1
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 08/09/2026 — electricity/rotation/heat authority, motors/generation, Energiser, wires, reactors, radiation, config/datapack, Aeronautics/CC:Tweaked boundaries e regressões 1.2.0 catalogados.
- **Histórico da decisão:** Houve sugestão histórica de remoção para simplificar a arquitetura energética; posteriormente o usuário informou que manteve alguns addons Create por preferência. Isso não constitui decisão final individual. Em 08/09/2026, Create: New Age foi reconfirmado como `Instalado`, permanece `Sem decisão` e a antiga data sem decisão formal foi removida.
- **Data da última decisão:**

# Dossiê operacional — padrão Alex's Mobs

> ⚡ Versão física confirmada: `create-new-age-1.2.0+neoforge-mc1.21.1.jar`, mod id `create_new_age`, runtime `1.2.0+mc1.21.1`. Na linha 1.21.1, o upstream declara **Create como requisito obrigatório** e não Botarium.

## 1. Papel e authority
Create: New Age adiciona uma camada elétrica/térmica própria ao ecossistema Create. **Create** continua authority das kinetic networks, stress, boilers e steam engines; New Age controla sua eletricidade, geração, motors, wires, heat network, Energiser e reactor state.

## 2. Geração elétrica
O projeto documenta geração de eletricidade por grandes **copper coils girando com magnets**. A energia produzida deve derivar do state cinético real do Create; bridges externas não devem creditar energia novamente pelo mesmo giro.

## 3. Motors
New Age oferece múltiplos motors que fazem o caminho inverso: eletricidade → rotação. Conversão deve manter um único settlement de energia e kinetic output; outro bridge não deve debitar o mesmo FE/state e criar uma segunda fonte de RPM.

## 4. Energiser
O **Energiser** cria itens usando rotação e eletricidade. Recipe/data do addon é authority de input, custo e output; JEI/Ponder são apenas apresentação.
KubeJS/datapack overrides precisam remover/substituir o recipe original explicitamente para não manter duas rotas concorrentes.

## 5. Wires
Wires transportam energia entre componentes. A 1.2.0 melhora **wire rendering** e corrige crashes com Create Aeronautics. Connection topology e energia transferida são gameplay state; render da wire não pode controlar existência lógica da conexão.

## 6. Heat system
O addon possui sistema de calor que permite mover heat gerado por solar panels e nuclear reactors. Heat é uma authority própria do New Age e não deve ser confundido automaticamente com temperature systems ambientais do jogador.
Integrações devem definir explicitamente qualquer conversão entre heat industrial e outra temperatura.

## 7. Solar panels
Solar panels geram **calor**, segundo a documentação oficial do projeto. O heat produzido pode ser transportado para aplicações posteriores; não modelar o painel como geração elétrica direta se a build não o define assim.

## 8. Nuclear reactors
New Age inclui **reatores nucleares multibloco** que geram grandes quantidades de calor, utilizável em boilers do Create para produção de steam/rotation.
Assembly, fuel/state, temperature e output precisam ser server-authoritative. Scripts externos devem observar o reactor final, não manter um segundo cálculo térmico paralelo.

## 9. Overheating e explosão — 1.2.0
A build 1.2.0 altera o risco operacional: **reactors now explode upon overheating**. Isso transforma temperatura/overheat em safety gate real.
QA deve validar escalation, persistência do heat, chunk unload e reload sem repetir explosion ou perder state crítico.

## 10. Radiation e Geiger counter — 1.2.0
A release adiciona **radiation effect** e ticking do **Geiger counter**. Radiation é efeito de gameplay do addon; visual/som do contador é feedback e não deve ser usado como única fonte para decidir aplicação/remoção server-side.

## 11. Street Lights e Lamp Posts
1.2.0 adiciona Street Lights e Lamp Posts. São conteúdo elétrico/decorativo do addon; light emission real precisa ser distinguida de emissive rendering de resource packs/shaders.

## 12. CC:Tweaked — WIP
A 1.2.0 adiciona compatibilidade **CC:Tweaked em estado WIP**, com pedido explícito de feedback pelo upstream. Não congelar peripheral methods/events em integração própria sem pin do source/JAR exato.

## 13. Create Aeronautics
A mesma release melhora wire rendering e **para crashes com Create Aeronautics**. O pack mantém Aeronautics, portanto wires/components em moving contraptions são regression surface prioritária.
Connection/capability references precisam ser invalidadas/re-resolvidas após assembly/disassembly.

## 14. Crafts & Additions
O FAQ oficial diz que New Age e **Create: Crafts & Additions** devem funcionar juntos. Isso comprova coexistência pretendida, não equivalência econômica.
Se ambos converterem energia↔rotação, balanceamento deve comparar taxas e progression gates; não desativar um automaticamente por compartilhar o domínio elétrico.

## 15. Monkey Edition datapack
O mod inclui datapack **Create: New Age [Monkey Edition]** para remover/simplificar recipes de conteúdo que o autor considera menos realista. Ativação desse datapack altera progression e precisa ser registrada como policy do modpack, não presumida pelo simples fato de o arquivo existir.

## 16. Configuração
O upstream confirma que o mod é configurável. Valores exatos de generation, heat, motors, reactor ou radiation não são congelados nesta ficha sem leitura da config física atual.
Config common/server é authority de balanceamento; mudanças precisam de restart/reload conforme suporte real.

## 17. Embedded ESL
O JAR físico contém `esl-neoforge-1.21.1-1.0.3+neoforge-mc1.21.1.jar` em `/META-INF/jars/`. É componente interno da distribuição e **não** deve ser catalogado como top-level.

## 18. Client/server e lifecycle
Energy, heat, reactor state, recipes e effects são common/server. Models, wires, Geiger feedback e lights têm apresentação client-side.
Validar server boot, chunk unload/reload, contraption assembly, reconnect, restart, config/datapack change e reactor recovery/failure.

## 19. Riscos
1. Energia↔rotação ser contabilizada duas vezes.
2. Heat industrial conflitar semanticamente com outro temperature system.
3. Reactor overheat/explosion repetir após reload.
4. Wire state divergir do render.
5. Aeronautics contraption manter wire reference stale.
6. CC:Tweaked WIP mudar API.
7. Dois energy addons criarem rota de progression excessivamente barata.
8. Embedded ESL ser tratado como top-level.

## 20. Matriz de testes
1. Dedicated server boot.
2. Coil/magnet generation em RPMs diferentes.
3. Motors: energia debitada e rotação criada uma vez.
4. Energiser recipes válidos/inválidos.
5. Wire connect/disconnect/chunk reload.
6. Solar heat e heat transport.
7. Reactor startup, heat output e boiler integration.
8. Overheat → explosion exatamente uma vez — regression 1.2.0.
9. Radiation/Geiger behavior.
10. Wires/components em Aeronautics contraption.
11. CC:Tweaked apenas se consumer realmente presente.
12. Coexistência com Crafts & Additions.
13. Monkey Edition datapack off/on em cópia de teste.

## 21. Evidência
- modlist física 08/09/2026: New Age 1.2.0+mc1.21.1;
- Modrinth oficial: coils/magnets, motors, Energiser, nuclear reactors, wires, solar heat e heat system; Create-only requirement em 1.21.1;
- changelog 1.2.0: lights, radiation/Geiger, reactor explosion on overheat, CC:Tweaked WIP e Aeronautics/wire rendering improvements;
- JAR físico: ESL embarcado, não top-level.

> 🔒 Boundary canônico: **New Age controla energia/calor/nuclear próprios; Create controla a cinética que ele consome ou produz**. Conversões devem ocorrer uma única vez em cada direction.