# NukaTeam's Gun Lib

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db81ddbee0f62adf7834af
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist 08.09.2026.txt` / `modlist.txt` — `ntgl-1.21.1-3.2.0.jar`, mod id `ntgl`, runtime `3.2.0`, mixin `ntgl.mixin.json`; Create: Gunsmithing `1.4.9` e GeckoLib `4.9.2` confirmados fisicamente
- **Data da exportação:** 2026-09-11

## Divergência documental detectada na exportação

- A página Notion declara procedência por uma `modlist.txt física canônica atual de 10/09/2026`. A authority física mais recente realmente acessível nesta execução permanece o snapshot de **08/09/2026 com 595 top-levels**. Nesse snapshot, NTGL 3.2.0, Create: Gunsmithing 1.4.9 e GeckoLib 4.9.2 estão confirmados.

## Propriedades do banco

- **Mod:** NukaTeam's Gun Lib
- **Arquivo JAR:** `ntgl-1.21.1-3.2.0.jar`
- **Versão 1.21.1:** 3.2.0
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Dependência
- **Categoria:** Biblioteca, RPG, Tecnologia
- **Função:** Framework de armas/gun packs para guns, melee, grenades e throwables, com dual wield, HUD, troca de munição/fire modes, entity gun use, death effects e projectiles bullet/laser/electric/flame/missile/grenade.
- **Dependências:** GeckoLib — Required Dependency oficial. Consumer físico confirmado: Create: Gunsmithing 1.4.9 requer NTGL + Create + playerAnimator. Outras integrações de backpacks/input são opcionais; VMinus é publicado como incompatível.
- **Sobreposição:** Não é arma isolada nem substituto direto de outras projectile libraries. É framework/authority compartilhada dos consumers NTGL; Create: Gunsmithing mantém ownership de seu conteúdo próprio.
- **Compatibilidade/Riscos:** Framework Client & Server requerido por Create: Gunsmithing. Riscos: Gunsmithing 1.4.9 cita baseline NTGL 3.1.8 enquanto pack usa 3.2.0; dual-wield/ammo state, Epic Fight/animation overlap, Sable sub-level projectiles e death/corpse lifecycle.
- **Observações:** Runtime 3.2.0, file ID 8585353, Release 05/08/2026. 3.2.0 adiciona Sable support para projectile/sub-level interaction e removal de attachments em survival. Gunsmithing 1.4.9 cita suporte NTGL 3.1.8; runtime 3.2.0 exige regressão, não downgrade automático.
- **Procedência:** modlist.txt física canônica atual de 10/09/2026 + CurseForge oficial NTGL 3.2.0 + relations/changelog oficiais + Create: Gunsmithing físico e sua dependency oficial NTGL.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/nukateams-gun-lib
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 10/09/2026 — NukaTeam's Gun Lib 3.2.0 reconstruído e reclassificado como Dependência por Create: Gunsmithing 1.4.9; gun packs, ammo/fire modes, dual wield, projectiles, Sable support, lifecycle, riscos e testes catalogados.
- **Histórico da decisão:** 2026-09-10 — reclassificado de Sem decisão para Dependência após confirmação causal de Create: Gunsmithing 1.4.9 instalado, cujo projeto declara NukaTeam's Gun Lib como Required Dependency.
- **Data da última decisão:** 2026-09-10

# Dossiê operacional — padrão Alex's Mobs

> **ESCOPO CANÔNICO.** Runtime físico: `ntgl-1.21.1-3.2.0.jar`, mod id `ntgl`, versão `3.2.0`, NeoForge 1.21.1. NukaTeam's Gun Lib é um framework Client & Server para armas animadas, gun packs, melee, granadas e projéteis. Neste pack existe um consumer causal confirmado: **Create: Gunsmithing 1.4.9**, que declara NTGL como Required Dependency. Portanto #429 deve ser tratado como **Dependência**. A release 3.2.0 também adiciona suporte explícito ao stack Sable.

## 1. Identidade e papel
- **Mod:** NukaTeam's Gun Lib / NTGL.
- **JAR físico:** `ntgl-1.21.1-3.2.0.jar`.
- **Mod id:** `ntgl`.
- **Runtime:** `3.2.0`.
- **Loader/jogo:** NeoForge 1.21.1.
- **Autor/ecossistema:** NukaTeam / Jetug.
- **Licença:** LGPLv2.1.
- **Ambiente:** Client & Server.
- **Papel:** framework de combate/armas para mods e gun packs, com animação, munição, fire modes, HUD e diferentes famílias de projéteis.
- **Decisão:** Dependência.

## 2. Consumer confirmado: Create: Gunsmithing
A modlist física contém `create-gunsmithing-1.21.1-1.4.9.jar`. A relação oficial do projeto Create: Gunsmithing exige:
- **Create**;
- **NukaTeam's Gun Lib**;
- **playerAnimator**.

Logo NTGL não é uma library órfã neste pack. Enquanto Create: Gunsmithing permanecer instalado, remover NTGL isoladamente quebra o contrato suportado do consumer.

A release 1.4.9 de Gunsmithing menciona suporte a **NTGL 3.1.8**; o pack usa NTGL 3.2.0. Isso não prova incompatibilidade, mas cria um baseline de regressão obrigatório porque a library está mais nova que a versão explicitamente citada pelo consumer.

## 3. Framework de gun packs
A documentação oficial apresenta um sistema de **gun packs** que permite adicionar/editar armas sobre a infraestrutura NTGL.

Boundary de ownership:
- NTGL é authority da infraestrutura comum de weapons/projectiles/input/sync que expõe;
- o gun pack/consumer é authority do conteúdo concreto — weapon IDs, ammo, stats, recipes, assets e balanceamento que registra;
- Create: Gunsmithing mantém ownership de suas armas/progressão Create-specific.

## 4. Tipos de conteúdo suportados
O projeto documenta suporte a:
- guns;
- melee weapons;
- grenades;
- throwables.

Essas categorias compartilham partes da infraestrutura, mas não devem ser tratadas como um único comportamento. Cada família precisa validar hit resolution, animation, ammo/resource consumption e server authority conforme aplicável.

## 5. Munição e troca de ammo
NTGL suporta **switching ammo types**. Isso implica estado de arma/seleção de munição e sincronização com inventário.

Riscos:
- ammo selecionada client-side divergir da aceita pelo server;
- troca rápida consumir stack errado;
- reload/troca durante lag duplicar ou perder ammo;
- gun pack registrar munições incompatíveis com o schema esperado pela 3.2.0.

## 6. Fire modes
A library oferece seleção de **fire mode**. O modo ativo pode alterar cadência/comportamento do disparo conforme cada arma.

O servidor deve validar cada shot e consumo de ammo. HUD/input apenas representa/intenciona a mudança; não deve ser a única authority do modo de tiro.

## 7. Dual wielding
**Dual wielding** é feature publicada do framework. É uma superfície de alta interação com:
- offhand inventory;
- animações dos braços;
- playerAnimator/NotEnoughAnimations;
- Epic Fight e outros combat systems;
- ammo consumption independente por mão.

O teste precisa confirmar que cada mão possui estado coerente e que reload/fire não duplica eventos.

## 8. Weapon HUD
O projeto fornece **HUD customizável** para armas. Essa camada é client-facing e deve refletir, não substituir, o estado autoritativo da weapon/ammo.

Conflitos possíveis:
- HUD mods;
- shaders/scaling;
- Modern UI/text rendering;
- overlays de combate;
- diferentes resoluções/UI scales.

## 9. Entidades usando armas
A documentação afirma que **living entities podem usar guns**. Isso amplia a library além do player input:
- AI/mobs podem disparar;
- projectile ownership precisa ser rastreado corretamente;
- dano/kills/loot credit devem ser consistentes;
- friendly-fire/faction behavior pertence ao consumer/AI que integra o uso.

Não inventar quais mobs do pack usam NTGL sem consumer específico.

## 10. Tipos de projétil integrados
O framework publica tipos built-in como:
- bullet;
- laser;
- electric;
- flame;
- missile;
- grenade.

Esses são contratos funcionais do framework. Valores de velocidade, dano, penetração, explosão ou alcance dependem da arma/gun pack e não são inferidos.

## 11. Mob death effects
NTGL suporta **mob death effects**. Isso toca a mesma região de lifecycle em que o stack Sable/Ragdoll também atua.

Não presumir conflito. O ponto técnico é testar:
- kill resolve uma vez;
- corpse/ragdoll não duplica a vítima;
- death effect visual não impede loot/XP;
- mods de resurrection/corpse não recebem entidade em estado impossível.

## 12. Sable support — release 3.2.0
O changelog exato 3.2.0 adiciona suporte ao **Sable**:
- projectiles deixam de atravessar sub-levels;
- projectiles podem destruir blocos de sub-levels conforme a integração;
- attachments podem ser removidos em survival;
- a release inclui crash fixes.

Isso é diretamente relevante porque o pack possui Sable e vários addons/patches. A compatibilidade é publicada pela release instalada; ainda assim deve ser regressada com o stack físico exato.

## 13. Attachments
A 3.2.0 permite remover **attachments em survival**. O estado de attachment pertence à weapon/item e precisa persistir corretamente após:
- equip/unequip;
- inventory transfer;
- death/recovery;
- reconnect;
- reload/restart.

O recipe/custo de attachment removal depende do consumer/config e não é inventado aqui.

## 14. Dependências e relações oficiais
A relação atual do projeto lista:
- **GeckoLib — Required Dependency**.

Também aparecem integrações opcionais como Backpacked, Controllable, playerAnimator, Sophisticated Backpacks, Subtle Effects, Traveler's Backpack e outras backpacks.

**VMinus** aparece como relação incompatível publicada.

O pack deve tratar only-installed relations como ativas. Uma integração opcional listada não prova presença física nem necessidade.

## 15. GeckoLib e animação
GeckoLib é required e fornece infraestrutura de animação para weapons/entities compatíveis. NTGL/consumers continuam authority do estado da arma; GeckoLib cuida da camada de animation data/render.

Em conjunto com playerAnimator, NotEnoughAnimations e Epic Fight, a prioridade é evitar transform concorrente de arms/body durante aim/reload/fire.

## 16. Networking e server authority
Por ser framework de armas em Client & Server, operações críticas precisam ser server-authoritative:
- validação do disparo;
- ammo consumption;
- hit/damage;
- projectile spawn;
- grenade/explosion effect;
- attachment mutation;
- weapon state relevante.

O cliente pode antecipar animação/som/HUD, mas reconciliação deve convergir ao estado do servidor.

Sem source pin exato nesta passagem, esta ficha não inventa nomes de packets/channels.

## 17. Gun pack/data lifecycle
Gun packs podem adicionar/editar conteúdo. Isso torna data/assets uma superfície de versionamento:
- weapon IDs não devem mudar sem migração;
- ammo/attachment definitions precisam combinar com a API 3.2.0;
- resource reload deve reconstruir assets sem deixar weapon state corrupto;
- server/client devem usar conteúdo compatível para evitar mismatch.

## 18. Compatibilidade com Create: Gunsmithing
Create: Gunsmithing é consumer central desta instalação. Testar especialmente:
- recipes/progressão Create produzem weapon NTGL válida;
- ammo e attachments reconhecidos;
- animation/reload/fire funcionam;
- updates da library não quebram serialização/data do gun pack;
- baseline citado pelo consumer (NTGL 3.1.8) versus runtime 3.2.0.

Não há evidência atual de incompatibilidade 3.2.0; o drift é risco de regressão, não motivo automático para downgrade.

## 19. Riscos
1. **Consumer/library drift:** Gunsmithing 1.4.9 cita NTGL 3.1.8; runtime é 3.2.0.
2. **Combat overlap:** Epic Fight e outros sistemas podem disputar input/animation/hit expectations.
3. **Dual wield state:** mão, ammo e cooldown podem divergir.
4. **Projectile/Sable integration:** sub-level collision/destruction é nova na 3.2.0.
5. **Death lifecycle:** mob death effects cruzam corpse/ragdoll systems.
6. **Optional backpack integration:** inventory adapters precisam evitar dupe/loss.
7. **Gun pack mismatch:** client/server data/assets divergentes podem produzir comportamento inconsistente.
8. **Attachment persistence:** remoção/equip precisa transacionar exatamente uma vez.
9. **GeckoLib/player animation:** rendering/arm transforms podem colidir.
10. **Incompatible VMinus:** relação oficial deve ser respeitada se esse mod vier a ser considerado no pack.

## 20. Matriz de testes
- [ ] Dedicated server e cliente iniciam com NTGL 3.2.0 + GeckoLib + Create: Gunsmithing 1.4.9.
- [ ] Arma Gunsmithing é criada/equipada e mantém dados após restart.
- [ ] Fire mode troca e sincroniza corretamente.
- [ ] Ammo type switch consome o stack correto exatamente uma vez.
- [ ] Reload/fire sob latência não duplica ammo/projétil.
- [ ] Dual wield dispara/recarrega cada mão sem state collision.
- [ ] Bullet/laser/electric/flame/missile/grenade representativos funcionam quando algum consumer realmente os usa.
- [ ] Living entity usando gun credita dano/kill corretamente.
- [ ] Attachment pode ser removido em survival e persiste após relog.
- [ ] Sable sub-level impede projectile de atravessar indevidamente e destruction segue a integração publicada.
- [ ] Death effects coexistem com Sable mob ragdoll corpses sem duplicate death/loot.
- [ ] NotEnoughAnimations/playerAnimator/Epic Fight não deixam arms/model presos após aim/reload/fire.
- [ ] Resource/data reload não invalida guns do consumer.

Nenhum teste foi marcado como aprovado nesta auditoria documental.

## 21. Evidências e limites
- Modlist física: `ntgl-1.21.1-3.2.0.jar`, mod id/runtime e `ntgl.mixin.json`.
- CurseForge oficial: project 998474, file ID 8585353, Release NeoForge 1.21.1 de 05/08/2026, Client & Server.
- Documentação oficial: gun packs, dual wield, HUD, ammo switching, fire modes, entity gun use, death effects e projectile families.
- Relations oficiais: GeckoLib required; integrações opcionais e VMinus incompatible conforme publicado.
- Changelog 3.2.0: Sable sub-level support, attachment removal in survival e crash fixes.
- Modlist física + relações oficiais de Create: Gunsmithing 1.4.9 confirmam NTGL como Required Dependency.
- **Limite:** nomes de packets/classes, valores balísticos e schemas internos não foram inventados sem source pin exato.
