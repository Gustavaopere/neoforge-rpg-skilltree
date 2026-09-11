# Particle Effects

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3d569db9f0db81708dface9d377b35ed
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist 08.09.2026.txt` / `modlist.txt` — `ParticleEffects-1.5.0+1.21.1+neoforge.jar`, mod id `particle_effects`, runtime `1.5.0+1.21.1+neoforge`, mixins `particle_effects-inventory_particles.mixins.json` + `particle_effects.mixins.json`; PartiCull `2.0`, Iris `1.8.14-beta.1`, Particle Rain `4.0.0-beta.11` e Particular Reforged `1.5.7` confirmados fisicamente
- **Data da exportação:** 2026-09-11

## Divergência documental detectada na exportação

- A página Notion declara procedência por uma “modlist.txt física canônica atual de 10/09/2026”. A autoridade física mais recente realmente acessível nesta execução permanece o snapshot de **08/09/2026 com 595 top-levels**. Nesse snapshot, Particle Effects 1.5.0 e o stack visual citado nos regression gates estão presentes. O corpo-fonte abaixo é preservado.

## Propriedades do banco

- **Mod:** Particle Effects
- **Arquivo JAR:** `ParticleEffects-1.5.0+1.21.1+neoforge.jar`
- **Versão 1.21.1:** 1.5.0+1.21.1+neoforge
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Visual, QoL
- **Função:** Mod visual client-side que adiciona partículas texturizadas específicas para efeitos vanilla. A representação é cosmética e não altera aplicação, duração ou mecânica dos efeitos.
- **Dependências:** Cliente NeoForge 1.21.1. Não foi confirmada hard dependency externa para a build física 1.5.0. O mod funciona em multiplayer como camada visual local.
- **Sobreposição:** Complementa o feedback visual de MobEffects; sobrepõe carga de rendering com Particle Rain/Particular e pode ser cullado por PartiCull, mas não duplica a lógica dos efeitos.
- **Compatibilidade/Riscos:** PartiCull pode cullar partículas dinamicamente; Iris/shaders alteram rendering; Particle Rain/Particular aumentam carga de partículas. Ausência visual não deve ser tratada como ausência do efeito lógico. Testar cleanup, dimension changes e stress.
- **Observações:** mod id `particle_effects`; runtime `1.5.0+1.21.1+neoforge`. Client-only. O upstream não publicou inventário textual completo dos particle registry IDs nesta release; nenhuma contagem foi inventada.
- **Procedência:** modlist.txt física canônica atual de 10/09/2026 + CurseForge oficial da build NeoForge 1.21.1 v1.5.0 e documentação oficial de multiplayer/resource packs.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/particle-effects
- **Atualização/Status:** PADRÃO ALEX'S MOBS REVALIDADO EM 10/09/2026 — Particle Effects 1.5.0; visual authority, effect lifecycle, resource packs, multiplayer, PartiCull/Iris boundaries, riscos e testes preservados e fonte física atualizada.
- **Histórico da decisão:**
- **Data da última decisão:** 2026-09-10

# Dossiê operacional — padrão Alex's Mobs

> **ESCOPO CANÔNICO.** Runtime físico: `ParticleEffects-1.5.0+1.21.1+neoforge.jar`, mod id `particle_effects`, versão `1.5.0+1.21.1+neoforge`. O mod é **client-side e puramente visual**: adiciona partículas texturizadas associadas aos efeitos vanilla, sem alterar a lógica dos efeitos.

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
- Modlist física canônica atual de 10/09/2026: JAR, mod id e versão.
- CurseForge oficial: ambiente Client, build NeoForge 1.21.1 1.5.0 e descrição de partículas únicas para efeitos vanilla.
- Página oficial: suporte a multiplayer e resource packs visuais.
- **Limite:** o upstream não publica inventário textual completo de particle registry IDs para esta build; quantidade/IDs e resource-pack overrides locais não foram inventados.
