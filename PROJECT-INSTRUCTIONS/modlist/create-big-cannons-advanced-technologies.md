# Create Big Cannons: Advanced Technologies

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db812290e7e79d9c57009f
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist.txt` — 595 mods top-level
- **Data da exportação:** 2026-09-10

## Propriedades do banco

- **Mod:** Create Big Cannons: Advanced Technologies
- **Arquivo JAR:** `cbc_at_Neoforge_1.21.1_0.1.4c.jar`
- **Versão 1.21.1:** 0.1.4c-1.21.1
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Tecnologia, Automação
- **Função:** Addon Create Big Cannons com 110 cannon/projectile blocks, Twin/Heavy Autocannons, Rocket Pods/Rails, muzzle brakes, fume extractors, silencers, rifled barrels e novas munições/rockets.
- **Dependências:** Required: Create + Create Big Cannons. Pack físico: Create 6.0.10 e Create Big Cannons 5.11.7. CBCAT Fix 1.0.1 está instalado como top-level separado imediatamente depois e é auditado separadamente neste mesmo lote.
- **Sobreposição:** Extensão bélica específica do CBC; Create/CBC continuam authorities de kinetic/contraption/cannon engine. CBCAT Fix é patch/addon separado, auditado neste mesmo lote; não fundir seus registries/recipes ao CBC:AT.
- **Compatibilidade/Riscos:** 0.1.4c foi publicada para compat CBC 5.11.3, enquanto pack usa CBC 5.11.7; QA de version drift obrigatório. Riscos: projectile/munition crashes, recoil/spread settlement, contraption/cannon assembly, ammo duplication e mixin overlap. CBCAT Fix está presente e deve ser auditado separadamente.
- **Observações:** Conteúdo oficial: 110 new cannon blocks = 20 Big Cannon, 28 Projectile, 6 Autocannon, 30 Twin Autocannon, 18 Heavy Autocannon, 6 Rocket Pod, 2 Medium Rocket Rail; items: 4 autocannon ammunitions, 3 heavy autocannon ammunitions, 4 rockets e 4 medium rockets. 0.1.4c corrige CBC 5.11.3 compatibility e crashes de Cluster Munition/Heavy Autocannon HEAT.
- **Procedência:** modlist.txt física atual de 08/09/2026 + CurseForge/Modrinth oficiais CBC: Advanced Technologies 0.1.4c + descrição/changelog oficiais + Create 6.0.10 e Create Big Cannons 5.11.7 físicos.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/create-big-cannons-advanced-technologies
- **Atualização/Status:** PADRÃO ALEX'S MOBS REVALIDADO EM 09/09/2026 — CBC: Advanced Technologies 0.1.4c-1.21.1 físico confirmado; 110-block release scope, cannon/ammunition families, CBC 5.11.3→pack 5.11.7 drift e CBCAT Fix separado preservados. Runtime QA não executado.
- **Histórico da decisão:** Sem decisão formal. Em 08/09/2026, Create Big Cannons: Advanced Technologies 0.1.4c-1.21.1 foi reconfirmado contra o JAR físico atual. CBCAT Fix é tratado como top-level separado no mesmo lote; a presença de ambos não foi convertida automaticamente em decisão de manter/remover.
- **Data da última decisão:**

# Dossiê operacional — padrão Alex's Mobs

> ✅ Versão física confirmada: `cbc_at_Neoforge_1.21.1_0.1.4c.jar`, mod id `cbc_at`, runtime `0.1.4c-1.21.1`, NeoForge 1.21.1. Required: Create + Create Big Cannons. O pack usa Create `6.0.10` e Create Big Cannons `5.11.7`.

## 1. Papel e authorities
CBC: Advanced Technologies expande Create Big Cannons com novas famílias de canhões, munições e componentes. **Create** continua authority de kinetic/contraption infrastructure; **Create Big Cannons** continua authority do cannon engine base; CBC:AT registra peças/munições/mecânicas adicionais.
Não implementar um segundo recoil/projectile/assembly pipeline em compat externa.

## 2. Escopo oficial — 110 cannon/projectile blocks
A descrição oficial da versão atual anuncia **110 new cannon blocks**, distribuídos em:
- 20 Big Cannon Blocks;
- 28 Projectile Blocks;
- 6 Autocannon Blocks;
- 30 Twin Autocannon Blocks;
- 18 Heavy Autocannon Blocks;
- 6 Rocket Pod Blocks;
- 2 Medium Rocket Rail Blocks.

A soma é 110 e é tratada como contagem release-facing do projeto.

## 3. Itens de munição/rockets
A descrição oficial anuncia:
- 4 new Autocannon Ammunitions;
- 3 Heavy Autocannon Ammunitions;
- 4 different Rockets;
- 4 different Medium Rockets.

Além disso existem blocks auxiliares como unbored cannon blocks e cast moulds. A ficha não infere IDs individuais ausentes da documentação oficial nesta etapa.

## 4. Quatro novos tipos de canhão
O projeto descreve quatro famílias principais:
1. **Twin Autocannon** — autocannon de dois canos;
2. **Heavy Autocannon** — calibre maior, cadência/breech apropriados a rounds maiores;
3. **Rocket Pod** — conjunto para múltiplos rockets;
4. **Medium Rocket Rail** — rails para rockets maiores.

Essas famílias se conectam aos contracts CBC, não constituem sistema de arma independente fora dele.

## 5. Big Cannon — Muzzle Brake
Muzzle Brake reduz recoil usando pressão dos gases no barrel. A documentação oficial indica relação com comprimento do barrel: maior comprimento reduz pressão e, portanto, reduz a eficiência relativa da redução de knockback/recoil.
Recoil deve settlement uma vez no cannon engine/provider; integração externa não deve aplicar uma segunda redução.

## 6. Fume Extractor
Reduz o tamanho da nuvem de partículas à frente do canhão, ajudando ocultação visual. Isso é majoritariamente apresentação/particle output; não interpretar automaticamente como redução de dano, alcance ou detecção de AI sem evidência específica.

## 7. Silencer
Silencer reduz ruído e também recoil em Big Cannons conforme descrição oficial. Variantes de silencer existem também em autocannon/twin autocannon.
Sound attenuation é client presentation; qualquer recoil modifier pertence ao state de canhão/server provider.

## 8. Rifled Barrel
Variante de barrel que reduz spread mais do que barrel normal. Spread deve ser calculado no firing pipeline real; não aplicar accuracy bonus novamente via item attribute global.

## 9. Twin Autocannon
Família oficial inclui:
- Breech;
- Recoil Spring;
- Barrel;
- Silencer;
- Muzzle Brake;
- variantes horizontais/verticais representadas no conteúdo/documentação.

Dois canos não significam automaticamente dois settlements por trigger externo; o provider decide firing sequence/projectile count.

## 10. Heavy Autocannon
Família de calibre maior com:
- Breech mais lento;
- Recoil Spring;
- Barrel;
- munições próprias, incluindo 3 classes anunciadas.

A 0.1.4c corrige crash associado a **Heavy Autocannon HEAT round**, logo essa munição é um gate de QA específico.

## 11. Rocket Pod
Rocket Pod possui barrel/pod e breech capaz de carregar múltiplos rockets; a descrição oficial cita até 15 rockets para o breech do pod.
Ammo count/reload state deve ser server-authoritative e persistir corretamente em contraption/chunk lifecycle.

## 12. Medium Rocket Rail
Rail system para rockets médios, com **quatro rails** para guiar quatro medium rockets e um Rail End como ponto inicial.
Não duplicar rockets por assemble/disassemble ou chunk reload.

## 13. Caseless / combined ammunition concept
A documentação descreve munições que combinam projectile + propellant num único bloco/round. Existem variantes fracas que aceitam propellant adicional e variantes fortes que falham quando recebem propellant extra.
Recipe/loading validation deve permanecer no CBC/CBC:AT pipeline; scripts externos não devem bypassar restrições de propellant.

## 14. Materiais e fabricação
O conteúdo histórico/documentado inclui componentes em materiais CBC como Cast Iron, Bronze, Steel e Nethersteel, além de unbored blocks e cast moulds.
Material tier não deve ser traduzido automaticamente para uma fórmula RPG externa sem usar properties reais do cannon block/projectile.

## 15. Release 0.1.4c
Changelog oficial da 0.1.4c confirma:
- compatibilidade com CBC `5.11.3`;
- fix de crash da **Cluster Munition** ao se dividir;
- fix de crash do **Heavy Autocannon HEAT round**;
- fix de crafting da Cluster Munition.

O pack usa CBC `5.11.7`, posterior ao alvo explícito do hotfix, portanto há **version drift real** a validar.

## 16. Create Big Cannons 5.11.7 no pack
CBC físico atual é `5.11.7`, cuja release suporta Create `6.0.7+`; o pack usa Create `6.0.10`.
Isso é coerente com o core CBC, mas não prova por si só que CBC:AT 0.1.4c foi testado contra 5.11.7. QA deve focar registries/mixins/firing/munition serialization.

## 17. CBCAT Fix separado
A modlist contém `cbcatfix-1.21.1-neoforge-1.0.1.jar` imediatamente depois de CBC:AT. Ele é **mod top-level separado** e é auditado separadamente neste mesmo lote físico.
Nesta página registra-se apenas a coexistência/ownership; as funções do fix permanecem documentadas na página própria CBCAT Fix.

## 18. Cannon/contraption lifecycle
Validar:
- cannon assembly/disassembly;
- mount/contraption movement;
- chunk unload/reload;
- server restart;
- breech ammo state;
- rocket pod/rail loaded state;
- firing durante contraption movement;
- break/reassemble sem item/projectile duplication.

Create/CBC permanecem authorities do lifecycle estrutural.

## 19. Projectile authority
Projectiles, submunitions e explosions são server-authoritative. Particles/sounds/tracers são client presentation.
Cluster splitting, HEAT impact e rocket launch devem gerar exatamente os projectiles/settlements definidos pelo provider, mesmo com latência ou chunk borders.

## 20. Recoil, spread, fume e sound
Separação de concerns:
- recoil: gameplay/physics state;
- spread: projectile trajectory state;
- fume: visual particle surface;
- sound/silencing: audio presentation com possíveis provider-side mechanics associados.

Não usar ausência de fume/sound para inferir que cannon não disparou.

## 21. Compatibilidade no pack
Pontos de conflito a observar:
- outros CBC addons registrando cannon parts/munitions;
- resource packs alterando models de barrels/projectiles;
- contraption mods modificando movement/assembly;
- damage/claims interceptando explosions;
- KubeJS recipes alterando ammunition crafting.

Sobreposição deve ser medida por registry/recipe/mixin concretos.

## 22. Riscos
1. Version drift CBC:AT 0.1.4c ↔ CBC 5.11.7.
2. Cluster Munition split crash/regression.
3. HEAT round crash/regression.
4. Ammo duplication em reload/assembly.
5. Recoil/spread modifier aplicado duas vezes.
6. Rocket Pod/Rail perder loaded state.
7. Client particle/sound callback disparar lógica server-side.
8. CBCAT Fix mascarar bug sem entender ownership.

## 23. Matriz de testes
1. Dedicated server boot com Create 6.0.10 + CBC 5.11.7 + CBC:AT 0.1.4c.
2. Smoke-test dos quatro cannon types.
3. Muzzle Brake: medir recoil comparativo por barrel length.
4. Rifled Barrel: comparar spread sem double modifier.
5. Silencer/Fume Extractor: audio/particles sem alterar firing settlement.
6. Twin/Heavy Autocannon: reload/fire/restart.
7. Rocket Pod com carga múltipla e restart.
8. Medium Rocket Rail com quatro rockets e contraption movement.
9. Cluster Munition split e crafting — regressões 0.1.4c.
10. Heavy Autocannon HEAT impact — regressão de crash.
11. Claims/protection/explosions.
12. Repetir com CBCAT Fix presente; registrar quais sintomas pertencem ao fix, com sua página auditada separadamente neste mesmo lote físico.

## 24. Evidência
- modlist física atual: CBC:AT 0.1.4c-1.21.1, CBC 5.11.7, Create 6.0.10;
- CurseForge/Modrinth oficiais: 110 block breakdown, ammunition/rocket counts e cannon families;
- documentação oficial de Muzzle Brake, Fume Extractor, Silencer, Rifled Barrel, Twin/Heavy Autocannon, Rocket Pod e Medium Rocket Rail;
- changelog 0.1.4c: CBC 5.11.3 compat + Cluster/HEAT fixes;
- CBCAT Fix identificado fisicamente como top-level separado, auditado neste mesmo lote.

> 💥 Authority canônica: CBC:AT adiciona peças/munições ao **engine Create Big Cannons**. A versão instalada merece QA específico contra CBC 5.11.7 porque o hotfix 0.1.4c cita explicitamente compatibilidade com CBC 5.11.3.
