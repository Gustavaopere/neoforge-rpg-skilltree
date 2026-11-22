# Particle Effects — 1.7.0 (versão instalada declarada em 22/11/2026)

> **Vigente em 22/11/2026:** `1.5.0+1.21.1+neoforge` → **`1.7.0`**, arquivo oficial `ParticleEffects-1.7.0-1.21.1-neoforge.jar`; instalação declarada pelo usuário para atualizar a modlist escrita. [Fonte](https://www.curseforge.com/minecraft/mc-mods/particle-effects/files/all). Nenhuma leitura de JAR local ou teste de Minecraft foi executada.

> **Cobertura técnica:** Releases 1.6.0 (22/09/2026) e 1.7.0 (20/11/2026) NeoForge 1.21.1. 1.6.0: minor rendering fixes sem detalhes de classes; o port 26.3 não pertence a 1.21.1. 1.7.0: compatibilidade com Item Particles e revisão da integração Inventory Particles para o novo layout de resources. A ausência dos mods opcionais no inventário não invalida a atualização.


## Propriedades do registro

- **Mod:** Particle Effects
- **Arquivo JAR instalado (declaração do usuário):** `ParticleEffects-1.7.0-1.21.1-neoforge.jar`; [publicação oficial](https://www.curseforge.com/minecraft/mc-mods/particle-effects/files/all).
- **Versão instalada declarada (Minecraft 1.21.1 / NeoForge):** `1.7.0` (antes: `1.5.0+1.21.1+neoforge`). O valor do metadata do JAR novo não foi extraído; o identificador é da publicação.
- **Categoria:** Visual, QoL
- **Decisão:** Sem decisão
- **Estado da pesquisa:** releases intermediárias revisadas a partir das páginas oficiais e do histórico já auditado, sem declaração de testes runtime.
- **Estado no pack:** `1.7.0` declarado instalado pelo usuário em 22/11/2026; inventário documental atualizado. JAR local não lido por este agente.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/particle-effects
- **Função:** Mod visual client-side que adiciona partículas texturizadas específicas para efeitos vanilla. A representação é cosmética e não altera aplicação, duração ou mecânica dos efeitos.
- **Dependências:** Cliente NeoForge 1.21.1. Não foi confirmada hard dependency externa para a build física 1.5.0. O mod funciona em multiplayer como camada visual local.
- **Compatibilidade/Riscos:** PartiCull pode cullar partículas dinamicamente; Iris/shaders alteram rendering; Particle Rain/Particular aumentam carga de partículas. Ausência visual não deve ser tratada como ausência do efeito lógico. Testar cleanup, dimension changes e stress.
- **Sobreposição:** Complementa o feedback visual de MobEffects; sobrepõe carga de rendering com Particle Rain/Particular e pode ser cullado por PartiCull, mas não duplica a lógica dos efeitos.
- **Observações atuais:** versão declarada `1.7.0`, distribuição `ParticleEffects-1.7.0-1.21.1-neoforge.jar`. As versões, hashes e afirmações “não instalada” anteriores são históricos datados.
- **Procedência:** a posição ordinal é do inventário top-level histórico; versão nova declarada pelo usuário e [página de distribuição](https://www.curseforge.com/minecraft/mc-mods/particle-effects/files/all); sem confirmação por hash/metadata do novo JAR local.
- **Atualização/Status:** 22/11/2026: `1.5.0+1.21.1+neoforge` → `1.7.0` conforme pedido. Releases 1.6.0 (22/09/2026) e 1.7.0 (20/11/2026) NeoForge 1.21.1. 1.6.0: minor rendering fixes sem detalhes de classes; o port 26.3 não pertence a 1.21.1. 1.7.0: compatibilidade com Item Particles e revisão da integração Inventory Particles para o novo layout de resources. A ausência dos mods opcionais no inventário não invalida a atualização.
- **Histórico da decisão:** 
- **Data da última decisão:** 2026-09-10

> **Autoridade física atual — 25/09/2026.** `modlist(1).txt` contém 587 entradas top-level incluindo o modloader; este item ocupa a ordem física #434: JAR `ParticleEffects-1.5.0+1.21.1+neoforge.jar`, mod id `particle_effects`, runtime `1.5.0+1.21.1+neoforge`, SHA-1 `cda7f6d1f8e164525b41fbb0899eb645c75fe3b9`.

<callout icon="✨" color="blue_bg">
	**ESCOPO CANÔNICO.** Runtime físico: `ParticleEffects-1.5.0+1.21.1+neoforge.jar`, mod id `particle_effects`, versão `1.5.0+1.21.1+neoforge`. O mod é **client-side e puramente visual**: adiciona partículas texturizadas associadas aos efeitos vanilla, sem alterar a lógica dos efeitos.
</callout>
## 1. Identidade, versão e papel
- **Mod:** Particle Effects.
- **JAR físico:** `ParticleEffects-1.5.0+1.21.1+neoforge.jar`.
- **Mod id:** `particle_effects`.
- **Versão instalada:** `1.5.0+1.21.1+neoforge`.
- **Loader/jogo:** NeoForge 1.21.1.
- **Ambiente:** Client.
- **Papel:** fornecer partículas visuais únicas/texturizadas para cada efeito vanilla suportado.
## 2. Authority e ownership
Particle Effects controla apenas **como estados de efeito já existentes são representados por partículas no cliente**. A aplicação, duração, amplificador, cura/dano, atributos e demais consequências dos efeitos permanecem sob authority vanilla ou do mod que aplicou o efeito.
O mod não deve ser usado como trigger de gameplay: ausência visual de uma partícula não prova ausência do efeito, especialmente quando culling/performance mods estão ativos.
## 3. Superfície funcional confirmada
A documentação oficial descreve um escopo simples e explícito:
- partículas texturizadas próprias para os efeitos vanilla;
- funcionamento em multiplayer;
- suporte a resource packs que substituem/ajustam a aparência das partículas;
- execução client-side.
A release 1.5.0 possui build NeoForge específica para Minecraft 1.21.1. O changelog público da família 1.5.0 é curto e não publica um inventário textual de cada textura/registry; por isso esta ficha não inventa uma contagem de particle types.
## 4. Renderização e lifecycle
O ciclo relevante é client-side:
1. o cliente recebe/possui o MobEffectInstance;
2. o mod decide/renderiza a partícula visual correspondente;
3. resource packs podem alterar a textura/aparência;
4. ao efeito terminar ou a entidade sair de contexto, a representação deve desaparecer normalmente.
Validar criação/remoção repetida de efeitos, morte/respawn, troca de dimensão, invisibilidade e relog.
## 5. Configuração e resource packs
A página oficial destaca resource packs adicionais que alteram a aparência das partículas. Nenhum desses packs foi assumido como ativo apenas por existir upstream.
A auditoria não leu config local nem resource-pack stack do usuário; portanto quantidade, estilo ou substituições concretas não são afirmadas.
## 6. Client / server
- **Client-only:** o servidor não precisa executar Particle Effects para manter efeitos ou regras de jogo.
- Em multiplayer, o servidor continua authority dos efeitos; o cliente apenas os visualiza.
- Dedicated server deve iniciar sem depender deste mod.
## 7. Integrações concretas no pack
- **PartiCull 2.0:** pode remover/reduzir partículas dinamicamente por desempenho; é a principal interação operacional. Particle Effects produz a camada visual; PartiCull pode cullá-la.
- **Iris 1.8.14-beta.1:** shader pipeline pode alterar aparência/transparência/depth das partículas, mas não seu estado lógico.
- **Particle Rain / Particular e outros particle mods:** aumentam a carga e diversidade do pipeline, elevando risco de overdraw e conflitos visuais.
- **Obscure Tooltips:** também usa partículas, porém em UI/tooltips; escopo diferente.
## 8. Riscos técnicos
1. **Particle overload:** múltiplos providers visuais podem elevar custo de frame.
2. **Culling:** PartiCull pode ocultar partículas; isso não deve ser interpretado como falha do efeito.
3. **Shaders:** blending/depth podem tornar partículas pouco visíveis ou excessivas.
4. **Resource-pack drift:** textura ausente/incorreta pode resultar em aparência inconsistente sem quebrar gameplay.
5. **Effect edge cases:** efeitos aplicados/removidos rapidamente, entidades invisíveis ou transitions de dimensão precisam de cleanup correto.
6. **Client/server interpretation:** nunca usar presença de partícula como state authoritative.
## 9. Multiplayer
O teste multiplayer deve verificar que dois clientes com configurações visuais diferentes observam o mesmo efeito lógico. Cliente sem o mod deve continuar jogando normalmente; cliente com o mod deve apenas acrescentar feedback visual.
## 10. Matriz de testes
- [ ] Cliente inicia com NeoForge build 1.5.0.
- [ ] Dedicated server inicia sem Particle Effects.
- [ ] Aplicar/remover efeitos vanilla de durações e amplificadores diferentes.
- [ ] Efeitos simultâneos em jogador e mobs.
- [ ] Morte/respawn e relog sem partículas órfãs.
- [ ] Troca Overworld/Nether/End sem stale particles.
- [ ] Multiplayer: cliente com e sem mod recebem o mesmo gameplay.
- [ ] PartiCull ativo sob FPS alto e baixo.
- [ ] Iris/shader ativo: transparência/depth corretos.
- [ ] Stress com Particle Rain/Particular + muitos mobs/effects.
Nenhum teste foi marcado como aprovado nesta auditoria.
## 11. Evidências e limites
- Modlist física canônica atual de 25/09/2026: JAR, mod id e versão.
- CurseForge oficial: ambiente Client, build NeoForge 1.21.1 1.5.0 e descrição de partículas únicas para efeitos vanilla.
- Página oficial: suporte a multiplayer e resource packs visuais.
- **Limite:** o upstream não publica inventário textual completo de particle registry IDs para esta build; quantidade/IDs e resource-pack overrides locais não foram inventados.


## 12. Atualização upstream 1.6.0 — não instalada
A autoridade física continua em **1.5.0+1.21.1+neoforge**. A próxima release publicada para 1.21.1 é **1.6.0**, de 22/09/2026.

O changelog compartilhado da 1.6.0 contém:
- port para Minecraft **26.3** — informação de manutenção multi-version, não comportamento atribuído ao JAR 1.21.1;
- **minor rendering fixes** — aplicável como delta visual potencial da build 1.21.1.

Como o upstream não discrimina quais rendering fixes afetam especificamente 1.21.1, não invento bugs ou partículas concretas. O regression gate permanece visual: blending/depth, cleanup de partículas, resource packs, shaders Iris, PartiCull, invisibility e dimension/relog.

Fonte upstream: CurseForge file ID 8948428, `ParticleEffects-1.6.0+1.21.1+neoforge.jar`.

## Auditoria incremental — Particle Effects 1.6.0 → 1.7.0 (22/11/2026; upstream)

**Físico:** #434 `ParticleEffects-1.5.0+1.21.1+neoforge.jar`, versão `1.5.0+1.21.1+neoforge`. As próximas publicações oficiais do projeto para **Minecraft 1.21.1 / NeoForge**, distintas dos builds 26.x/Fabric/Forge, são:

- **1.6.0 — 22/09/2026**: [CurseForge file 8948428](https://www.curseforge.com/minecraft/mc-mods/particle-effects/files/8948428), filename `ParticleEffects-1.6.0+1.21.1+neoforge.jar`. A descrição resumida no arquivo anuncia **correções menores de renderização**; também contém a nota geral “port to 26.3”, que **não** é uma alteração Minecraft 1.21.1. Não atribuir ao binário 1.21.1 mudanças de APIs/efeitos exclusivas da 26.3 nem inventar quais glitches foram resolvidos.
- **1.7.0 — 20/11/2026**: [catálogo CurseForge oficial do projeto](https://www.curseforge.com/minecraft/mc-mods/particle-effects/files/all) lista a release **`[neoforge/1.21.1] Particle Effects v1.7.0`**, para o alvo correto. O changelog geral distribuído nas builds 1.7.0 anuncia **compatibilidade com Item Particles** e **atualização da compatibilidade com Inventory Particles após a mudança no layout de resources**. A lista pública por arquivo para este target foi verificada, mas **não foi inspecionado o novo JAR** para assegurar a implementação/ativação exata de cada hook.

**Relevância no pack:** o mod modifica partículas de status visual no cliente; compatibilidades opcionais não provam que Item Particles ou Inventory Particles estejam instalados. Os efeitos de gameplay e estados server-side permanecem sob autoridade dos providers. QA: status effects em HUD/mundo, partículas em combates Iron's/Ars, Particle Rain/Particular, culling por PartiCull, shaders, recarregamento de resource packs, presença/ausência dos dois mods opcionais e impacto em FPS. **Nenhuma instalação, inspeção binária ou QA ocorreu.** Baseline v1.5.0 e categorias oficiais preservadas.

## Sincronização declarada da instalação — 22/11/2026

**Antes:** `1.5.0+1.21.1+neoforge`. **Agora:** `1.7.0`, arquivo `ParticleEffects-1.7.0-1.21.1-neoforge.jar` conforme distribuição/declaração de atualização.

**Mudanças auditadas:** Releases 1.6.0 (22/09/2026) e 1.7.0 (20/11/2026) NeoForge 1.21.1. 1.6.0: minor rendering fixes sem detalhes de classes; o port 26.3 não pertence a 1.21.1. 1.7.0: compatibilidade com Item Particles e revisão da integração Inventory Particles para o novo layout de resources. A ausência dos mods opcionais no inventário não invalida a atualização.

**Fonte:** https://www.curseforge.com/minecraft/mc-mods/particle-effects/files/all. Trechos pretéritos com “físico atual” ou “não instalada” descrevem o snapshot antes desta solicitação, não o estado documental vigente. Não foram executados testes em mundo, cliente ou servidor.
