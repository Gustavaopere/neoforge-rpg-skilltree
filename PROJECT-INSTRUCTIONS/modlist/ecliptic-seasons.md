# Ecliptic Seasons

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db810ca9d0ee8a2ad1f774
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist 08.09.2026.txt` / `modlist.txt` — JAR confirmado fisicamente
- **Data da exportação:** 2026-09-10

## Propriedades do banco

- **Mod:** Ecliptic Seasons
- **Arquivo JAR:** `EclipticSeasons-1.21.1-neoforge-0.15.0-rc-3.jar`
- **Versão 1.21.1:** 0.15.0-rc-3
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Clima, Worldgen
- **Função:** Provider sazonal/climático do pack, baseado em 24 termos solares, que altera ambiente, clima, neve, ecologia, agricultura e opcionalmente sobrevivência ao longo do ano.
- **Dependências:** NeoForge 1.21.1. Extensões instaladas: MultiMod Patch 0.32.1 e Bundles 0.18.0.2. Distant Horizons 3.2.0-b está presente e a linha 0.15.0 possui integração oficial de aparência sazonal em LODs.
- **Sobreposição:** É o provider de estação/clima sazonal do stack. Cold Sweat trata temperatura corporal; MultiMod Patch traduz estado sazonal; Bundles adiciona dados/recursos. Não são substitutos do core.
- **Compatibilidade/Riscos:** Build 0.15.0-rc-3 é Beta/RC. Riscos: configuração migrada de versões antigas, seasonal simulation level produzir alteração de gameplay além do desejado, snow behavior competir com outros render/world mods, crop/humidity/greenhouse drift, Distant Horizons LOD stale e dupla tradução de temperatura via adapters.
- **Observações:** 0.15.0 introduz níveis Environment, Ecology, Agriculture, Survival e Custom; Snow Behavior separado; config screen redesenhada; datapack content pode reagir ao nível escolhido; Distant Horizons ganhou reprodução de seasonal model appearance. Runtime físico permanece 0.15.0-rc-3 para 1.21.1.
- **Procedência:** Modlist física canônica de 08/09/2026 (595 top-levels) confirma `EclipticSeasons-1.21.1-neoforge-0.15.0-rc-3.jar`, mod id `eclipticseasons` e versão 0.15.0-rc-3. CurseForge oficial File 8798533 confirma NeoForge 1.21.1 Beta de 03/09/2026 e changelog 0.15.0.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/ecliptic-seasons
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 09/09/2026 — 0.15.0-rc-3; 24 termos solares, níveis de simulação, weather/snow/agriculture/ecology/survival, config, Distant Horizons, lifecycle, riscos e testes catalogados.
- **Histórico da decisão:**
- **Data da última decisão:** 2026-08-26

# Dossiê operacional — padrão Alex's Mobs

> **Runtime físico confirmado:** `EclipticSeasons-1.21.1-neoforge-0.15.0-rc-3.jar` · mod id `eclipticseasons` · versão `0.15.0-rc-3` · NeoForge 1.21.1 · canal oficial Beta/RC.

## 1. Papel no modpack
Ecliptic Seasons é o **provider sazonal/climático** do pack. Seu calendário usa 24 termos solares e faz o mundo responder ao passar do ano por clima, precipitação, neve, cores, vegetação, crops e outros sistemas conforme o nível de simulação escolhido.

## 2. Authority / ownership
- **Ecliptic Seasons:** estação, termo solar e regras sazonais.
- **Minecraft/worldgen providers:** biomas, blocos e weather state-base que o mod consulta/adapta.
- **Cold Sweat:** temperatura corporal do jogador; integração sazonal vem pelo MultiMod Patch.
- **Dynamic Trees:** growth engine; o contexto sazonal é fornecido via patch.
- **Bundles/MultiMod Patch:** compatibilidade, não calendário paralelo.

## 3. 24 termos solares
A descrição oficial define o sistema em torno de **24 solar terms**, usados como granularidade temporal para alterar weather, crops e ecossistemas. Integrações próprias devem consultar o state/provider sazonal, não aproximar a estação por dia do mundo em fórmula paralela.

## 4. Seasonal Simulation Levels — 0.15.0
A linha física 0.15.0 introduz níveis explícitos:
- **Environment:** cenário, weather, snow e atmosfera sem mudanças de gameplay;
- **Ecology:** blocos naturais, vegetação e ecossistemas passam a responder às estações;
- **Agriculture:** inclui crops, humidity, farming e greenhouses;
- **Survival:** experiência completa, incluindo animals, temperature e survival mechanics;
- **Custom:** features sazonais configuradas individualmente.

Esse nível é um contrato operacional importante: modpacks não devem assumir que toda feature sazonal está ativa sem ler a configuração.

## 5. Snow Behavior
A 0.15.0 separa o controle de **snow rendering** e das mecânicas vanilla de snow/ice por uma opção própria de Snow Behavior. Isso permite alterar apresentação e mecânica com granularidade maior.

Risco: outro mod de neve/render tentar aplicar uma segunda camada sobre o mesmo bloco/LOD.

## 6. Datapack-aware simulation
Recipes, advancements, loot tables e outros conteúdos de datapack podem adaptar-se ao simulation level escolhido. Consequência: alterar o nível sazonal pode mudar mais do que o visual e deve ser tratado como mudança de gameplay/configuração do pack.

## 7. Configuração 0.15.0
A interface de configuração foi redesenhada com navegação, busca e descrições melhores. O framework de configuração também pode ser reutilizado por addons do Ecliptic Seasons.

A versão inclui migração automática para opções renomeadas/reorganizadas quando possível. Mesmo assim, upgrades devem verificar o arquivo resultante e não pressupor que toda key antiga converteu semanticamente sem mudança.

## 8. Distant Horizons
A 0.15.0 melhora a integração com **Distant Horizons**, presente no pack como 3.2.0-b. LODs podem reproduzir aparências do seasonal model system; também houve melhorias para snow, frozen water, color calculation e world reloading.

Regression gate: mudança de termo solar com LODs já gerados, neve/gelo distante e reload do mundo sem aparência stale.

## 9. Voxy — limite
A release 0.15.0 também reestrutura compatibilidade Voxy, mas para 1.20.1/1.21.1 o próprio changelog direciona a um mod de compatibilidade separado. Isso não é tratado como funcional no pack sem presença física desse bridge.

## 10. Falling leaves e aparência
A linha 0.15.0 permite que folhas caídas permaneçam brevemente no chão e desapareçam gradualmente; também corrige frequência de partículas relacionada. Isso é uma superfície visual/ambiental, não autoridade de crop/growth.

## 11. Performance / render
O changelog registra mixins mais leves em paths de alta frequência, aplicação mais precisa de compatibility mixins por mod/version, melhoria de snow-covered model generation, foliage color blending para Embeddium e correção de heightmap que podia causar stutter durante chunk loading.

Performance real deve ser medida no pack; o changelog não prova ausência de regressão com shaders/LOD/worldgen atuais.

## 12. Agriculture / humidity / greenhouses
No nível Agriculture, crops, humidity, farming e greenhouses entram no sistema sazonal. Bundles e outros adapters podem adicionar regras para conteúdo modded.

A autoridade do crop continua no provider do crop; Ecliptic Seasons fornece a condição sazonal. Scripts externos não devem aplicar outra penalidade/boost equivalente sem precedence explícita.

## 13. Survival e temperatura
No nível Survival, animals, temperature e outras mechanics podem responder às estações. Como o pack usa Cold Sweat, a variação de temperatura deve passar pela integração existente e ser testada para evitar double offset.

## 14. Client / Server
- termo solar, clima, crop/ecology/survival rules: server/common-authoritative;
- seasonal colors/models/particles/LOD: client-facing;
- config que afeta gameplay precisa convergir no servidor;
- cliente não deve decidir growth, harvest eligibility ou temperatura corporal final.

## 15. Lifecycle
Validar:
- world creation;
- avanço de termo solar;
- restart/reconnect;
- dimension change;
- chunk unload/reload;
- config migration;
- datapack/resource reload;
- Distant Horizons reload/rebuild;
- alteração do simulation level em mundo de teste;
- transição de snow/freeze states.

## 16. Multiplayer
O servidor deve fornecer o mesmo state sazonal para todos os jogadores. Clientes podem ter diferenças puramente gráficas, mas não podem divergir em crop growth, weather eligibility ou survival modifiers.

## 17. Riscos
1. RC/Beta regression;
2. config antiga migrada com semântica diferente;
3. simulation level mais profundo que o esperado pelo pack;
4. snow rendering/mecânica duplicada;
5. crop/humidity rule aplicada duas vezes;
6. Cold Sweat receber double seasonal offset;
7. Dynamic Trees receber mais de um seasonal modifier;
8. Distant Horizons mostrar LOD stale;
9. chunk old/new visual/gameplay seam;
10. datapack condicional resolver nível errado;
11. reload deixar cache sazonal inconsistente;
12. performance/render regressions com o stack gráfico.

## 18. Matriz de testes
1. Dedicated server boot com core + MultiMod Patch + Bundles.
2. Confirmar os 24 termos/avanço temporal em mundo de teste.
3. Testar cada Simulation Level em cópia separada.
4. Weather/snow em biomas quentes, temperados e frios.
5. Crops/humidity/greenhouse no nível Agriculture.
6. Cold Sweat no nível Survival sem double offset.
7. Dynamic Trees em estações de crescimento favorável/desfavorável.
8. Distant Horizons: foliage/snow/frozen water em LOD antes/depois da mudança sazonal.
9. Config migration a partir de backup de config antigo.
10. Datapack/resource reload.
11. Multiplayer com dois clientes em regiões/biomas diferentes.
12. Profile de chunk loading e render em área de alta distância.

**Esta catalogação não afirma que esses testes foram executados.**

## 19. Evidências
- modlist física canônica de 08/09/2026: JAR/mod id/versão;
- CurseForge oficial File 8798533, NeoForge 1.21.1 Beta;
- changelog oficial 0.15.0: Simulation Levels, Snow Behavior, config redesign/migration, Distant Horizons, falling leaves, performance/compatibility.

> **Boundary canônico:** Ecliptic Seasons é a fonte de verdade do **estado sazonal**. Addons apenas traduzem esse state para conteúdo externo.
