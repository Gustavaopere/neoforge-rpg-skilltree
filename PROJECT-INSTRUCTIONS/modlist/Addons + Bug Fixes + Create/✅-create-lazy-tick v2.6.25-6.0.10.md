# Create: Lazy Tick

> **Autoridade física atual — 23/09/2026.** `modlist(1).txt` contém **587 entradas top-level incluindo o modloader**; este item ocupa a ordem física **#191**: JAR `CreateLazyTick-2.6.25-6.0.10-neoforge-1.21.1.jar`, mod id `createlazytick`, runtime `2.6.25-6.0.10`, SHA-1 `0c95270c36bc112e5dc95c962811d3aa3946ffb5`.

## Propriedades do registro

- **Mod:** Create: Lazy Tick
- **Arquivo JAR:** `CreateLazyTick-2.6.25-6.0.10-neoforge-1.21.1.jar`
- **Versão 1.21.1:** `2.6.25-6.0.10`
- **Categoria:** Performance, Tecnologia
- **Função:** Camada de performance para Create que reduz trabalho de tick e otimiza caches/sincronização em componentes Create sem assumir ownership da semântica funcional das máquinas.
- **Dependências:** Create 6.0.10 é dependência central e a build física é explicitamente alinhada a essa versão; pack usa NeoForge 21.1.248. Integrações com addons como Create Big Cannons são superfícies de compatibilidade, não dependências-base universais.
- **Estado no pack:** Integrado ao Github
- **Estado da pesquisa:** Verificado
- **Compatibilidade/Riscos:** Otimizações alteram cadência de tick/cache em componentes Create; riscos incluem stale recipe/cache, machine wakeup atrasado, redstone timing, fluid state, addon subclass incompatível e regressão de exactly-once. Upstream 2.7.29/2.7.30 amplia a superfície para Factory Gauges, funnels/redstone, belts/passengers, Central Kitchen saw cache e bounded Redstone Link refresh.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/create-lazytick
- **Procedência:** modlist física atual + CurseForge oficial 2.6.25/2.7.29/2.7.30 + source oficial `duckgun13476/Create-LazyTick` branch `CLT-combined`. A versão instalada continua 2.6.25-6.0.10.
- **Observações:** Runtime físico permanece `2.6.25-6.0.10`. No CurseForge, as próximas releases publicadas localizadas para 1.21.1/Create 6.0.10 são **2.7.29** (25/09/2026) e **2.7.30** (28/09/2026). O source contém preparação de 2.6.27, mas não foi localizado artefato CurseForge 1.21.1 correspondente; por isso não é tratado como release distribuída.
- **Atualização/Status:** ATUALIZAÇÃO UPSTREAM REVALIDADA EM 01/10/2026 — Create:LazyTick físico permanece 2.6.25-6.0.10. Foram revisadas as releases publicadas **2.7.29 → 2.7.30**; 2.7.30 é a latest 1.21.1/Create 6.0.10 no CurseForge.
- **Decisão:** Sem decisão
- **Histórico da decisão:** Sem decisão formal. Em 08/09/2026, Create: Lazy Tick 2.6.25-6.0.10 foi reconfirmado como `Instalado` e reconstruído ao padrão técnico; benefício de performance não foi convertido automaticamente em decisão curatorial.
- **Sobreposição:** Não duplica CreateBetterFps: Lazy Tick atua em tick scheduling/caches/sync; CreateBetterFps atua em outra superfície de performance/render. Conflito real depende de hooks concretos sobre o mesmo state Create.

# Dossiê operacional — padrão Alex's Mobs
> ⏱️ Versão física confirmada: `CreateLazyTick-2.6.25-6.0.10-neoforge-1.21.1.jar`, runtime `2.6.25-6.0.10`. É uma camada de **otimização de tick/cache/sync** específica para Create 6.0.10; não deve alterar a semântica funcional das máquinas.
## 1. Papel e authority
Create: Lazy Tick otimiza atualização de componentes Create por scheduling, caches e redução de trabalho repetido. **Create continua authority do estado funcional** de belts, funnels, chutes, depots, basins, deployers, arms e demais máquinas; Lazy Tick só pode mudar quando/como trabalho equivalente é recalculado.
## 2. Princípio de invariância
Toda otimização precisa preservar a mesma consequência lógica do Create sem o mod: item count, fluid amount, recipe completion, redstone state e machine progression não podem mudar apenas porque um cache evitou trabalho.
## 3. Tick scheduling
A linha do projeto reduz frequência/trabalho de ticks onde o state não exige atualização completa. O risco técnico é adiar uma transição que deveria ocorrer imediatamente após inventory, neighbor, recipe ou network change.
## 4. Caches
Caches podem evitar lookups repetidos em recipes, inventories e regras de interação. Qualquer evento que invalide o state precisa invalidar também o cache correspondente; caso contrário surgem phantom recipes, item loss ou comportamento stale.
## 5. CBC ammo containers — 2.6.25
A 2.6.25 corrige **Create Big Cannons ammo containers que desapareciam ou deixavam de ser reabastecidos** quando Mechanical Arm reutilizava cached Deployer recipe. Regression gate: container e ammo precisam permanecer consistentes após múltiplos ciclos Arm↔Deployer.
## 6. Basin resume — 2.6.25
A build corrige máquinas operadas por **Basin que falhavam em retomar** depois que um full stack de output era extraído. Output extraction deve invalidar/reavaliar o state necessário para a máquina continuar, sem exigir reload manual.
## 7. Mechanical Arm rules após resource reload
2.6.25 mantém as regras de compatibilidade do **Mechanical Arm atualizadas após resource reload**. Datapack/resource changes não podem deixar cached acceptance rules apontando para recipes/targets antigos.
## 8. Lazy Clock sync queue
A release reduz overhead da fila de network sync do **Lazy Clock**. Isso é otimização de sincronização; o clock/state server-side continua authority e o cliente deve convergir sem gerar backlog ou updates duplicados.
## 9. Componentes Create afetados pela linha
A linha Lazy Tick atua sobre várias superfícies Create, incluindo belts, funnels, chutes, depots, Basin/Deployer/Mechanical Arm e outros componentes conforme versão. Esta ficha não presume que todo componente usa exatamente o mesmo mecanismo de cache; cada regressão deve ser localizada.
## 10. Recipes e reload
Recipe/data reload é uma boundary crítica porque invalida assumptions previamente cacheadas. Após reload, máquinas devem reconhecer recipes novos/removidos e não continuar executando uma versão antiga por referência stale.
## 11. Inventories e capabilities
Inventory/capability references podem mudar em chunk unload, contraption assembly ou block replacement. Cache não pode manter handler inválido e inserir/extrair de um inventory que já não é owner do state.
## 12. Performance versus correctness
Ganho de tick só é válido se o comportamento final for equivalente. Medir TPS/MSPT e packet volume junto com conservation tests; otimização que perde items, atrasa indefinitely ou duplica output não é aceitável mesmo se reduzir custo de CPU.
## 13. Client/server
Machine state e inventories são server/common. Config/overlay/Lazy Clock presentation e parte do network sync têm client-facing surfaces. Cliente não deve decidir que uma máquina pode pular processamento ou aceitar item com base em cache local.
## 14. Lifecycle
Validar startup, chunk unload/reload, server restart, datapack/resource reload, Create recipe changes, block replacement, capability invalidation e update de addons Create que tocam as mesmas máquinas.
## 15. Riscos
1. Cache stale executar recipe removido.
2. Item/fluid perdido ou duplicado por invalidation incorreta.
3. Basin permanecer travado após output extraction.
4. CBC ammo container desaparecer/não refill.
5. Mechanical Arm usar regra antiga após reload.
6. Tick reduzido atrasar state change indefinidamente.
7. Sync queue criar backlog/desync visual.
8. Create/addon version drift quebrar mixins/hooks.
## 16. Matriz de testes
1. Dedicated server boot com Create 6.0.10.
2. Belts/funnels/chutes/depots em carga contínua.
3. Basin: full-stack output extraction → retomada automática — regression 2.6.25.
4. CBC ammo container + Mechanical Arm + Deployer por vários ciclos — regression 2.6.25.
5. Resource reload alterando recipe/Arm target rules.
6. Chunk unload/reload durante processamento.
7. Server restart com machines parcialmente processadas.
8. Inventory/block replacement invalidando capabilities.
9. Lazy Clock/network sync sob múltiplos dispositivos.
10. Comparar TPS/MSPT/packet behavior com correctness checks, não apenas performance bruta.
## 17. Evidência
- modlist física 08/09/2026: Lazy Tick 2.6.25-6.0.10;
- release oficial 2.6.25: CBC ammo container/Mechanical Arm cached Deployer fix, Basin resume fix, Mechanical Arm rules após resource reload e menor overhead da Lazy Clock sync queue;
- documentação da linha: otimizações de ticks/caches para componentes Create e Lazy Clock/config/overlay.
> 🔒 Boundary canônico: **Lazy Tick pode reduzir trabalho, mas não pode mudar o resultado lógico do Create**. Cada otimização deve ser validada por invariância de state antes de considerar o ganho de performance seguro.


## Atualizações upstream 2.7.29 → 2.7.30 — não instaladas
A autoridade física continua em **2.6.25 para Create 6.0.10**.

### 2.7.29 — 25/09/2026
- corrige Basins ignorando um **segundo fluid input** ao avaliar recipes;
- reduz overhead de monitoramento passivo de **Factory Gauges** em Create 6.x;
- corrige cache de recipe de **Mechanical Saw com Central Kitchen**;
- corrige progresso de cache de **sequenced Deployer**;
- corrige carregamento de Mixins no NeoForge e restaura a recipe do **Lazy Tick Clock** em 1.21.1;
- corrige funnels voltando a funcionar após pausa por redstone, incluindo **belt funnels**;
- adiciona **funnel redstone overclocking** opcional, desabilitado por padrão porque pode aumentar lag;
- reduz overhead de passageiros `LivingEntity` travados/parados sobre belts.

### 2.7.30 — 28/09/2026
- adiciona suporte Fabric 1.20.1/Create Fabric 6.0.8.1 — manutenção de outra plataforma, não projetada sobre este runtime NeoForge;
- restaura **bounded Redstone Link refreshes para sinais inalterados**, sem interceptar transmissões explícitas.

Impacto: 2.7.29 toca recipe correctness e wakeup/timing em componentes centrais do pack; 2.7.30 ajusta a fronteira de otimização de wireless redstone para manter refresh periódico mesmo sem mudança de sinal. Ambos precisam ser testados contra factories reais, Central Kitchen e addons que subclassificam Create machines.

Gate de promoção: Basin com dois fluid inputs; Factory Gauge estável/mutável; Saw + Central Kitchen; sequenced Deployer; Lazy Tick Clock recipe/UI; funnel e belt funnel pausados por redstone; overclock off/on sob carga; living passengers parados em belts; Redstone Links com sinal estável e mudança explícita; `/reload`; restart; CBC ammo/deployer regressions de 2.6.25.

Fontes upstream: CurseForge file IDs 8970326 (2.7.29) e 8995867 (2.7.30); source `duckgun13476/Create-LazyTick`.