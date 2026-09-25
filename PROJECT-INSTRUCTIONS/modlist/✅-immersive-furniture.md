# Immersive Furniture

## Propriedades do registro

- **Mod:** Immersive Furniture
- **Arquivo JAR:** `immersive_furniture-neoforge-0.3.3+1.21.1.jar`
- **Versão 1.21.1:** `0.3.3+1.21.1`
- **Categoria:** QoL, Visual
- **Decisão:** Manter
- **Estado da pesquisa:** Verificado
- **Estado no pack:** Integrado ao Github
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/immersive-furniture
- **Função:** Sistema de furniture customizada com editor in-game e biblioteca online; suporta partículas, sons, sitting/sleeping, material-based texturing, estados alternáveis e lighting.
- **Dependências:** NeoForge 1.21.1. Client & Server. O JAR físico embarca `sable-companion-common-1.21.1-1.5.0.jar` (`sablecompanion` 1.5.0) em `META-INF/jarjar`; é dependência interna do host. Sable é integração relevante no pack e teve crash fix na linha 0.3.2; não foi tratado como hard dependency universal além do componente embarcado.
- **Compatibilidade/Riscos:** Riscos: furniture entity/state desync, pose/collision, conteúdo remoto não reprodutível, custo em builds densas e transforms em Sable. 0.3.2 corrige crashes Sable; 0.3.3 corrige particle emitter rotation.
- **Sobreposição:** Sobreposição temática com furniture/decor mods, mas editor/importação de objetos customizados e library remota são superfícies próprias.
- **Observações:** JAR físico `immersive_furniture-neoforge-0.3.3+1.21.1.jar`, mod id `immersive_furniture`, runtime 0.3.3+1.21.1. Nested `sablecompanion` 1.5.0 é JarJar interno e não recebe entrada top-level. Release 0.3.3 permanece a build NeoForge 1.21.1 mais recente localizada.
- **Procedência:** modlist.txt física anexada e reconferida em 12/09/2026 + CurseForge oficial Immersive Furniture 0.3.3 NeoForge 1.21.1 + changelog 0.3.1–0.3.3 + inventário físico do JAR confirmando `sable-companion-common-1.21.1-1.5.0.jar` embarcado em `META-INF/jarjar`.
- **Atualização/Status:** PADRÃO ALEX'S MOBS REVALIDADO EM 12/09/2026 — Immersive Furniture 0.3.3+1.21.1/JAR físico reconfirmado como release NeoForge 1.21.1 mais recente localizada. Correção material: `Sable Companion 1.5.0` está embarcado via JarJar no host e foi documentado como dependência interna, não top-level.
- **Data da última decisão:** 2026-09-06

> **Autoridade física atual — 24/09/2026.** `modlist(1).txt` contém 587 entradas top-level incluindo o modloader; este item ocupa a ordem física #328: JAR `immersive_furniture-neoforge-0.3.3+1.21.1.jar`, mod id `immersive_furniture`, runtime `0.3.3+1.21.1`, SHA-1 `cd595ea729f0b55a09568c2ebcdb1ad2d6c16c53`. `sable-companion-common-1.21.1-1.5.0.jar` permanece JarJar interno do host e não ocupa posição top-level.

<callout icon="🪑" color="green_bg">
	**ESCOPO CANÔNICO.** Runtime físico: `immersive_furniture-neoforge-0.3.3+1.21.1.jar`, mod id `immersive_furniture`, versão `0.3.3+1.21.1`. É um sistema Client & Server para criar, importar e usar furniture customizada, não apenas um pack estático de modelos.
</callout>
## 1. Editor e biblioteca
O projeto fornece editor in-game e biblioteca online comunitária. A documentação pública registra mais de 3500 peças disponíveis, mas esse número é catálogo remoto e muda sem troca do JAR; não deve ser tratado como registry count fixo da build.
O Artisan's Workstation é o ponto de entrada documentado para criação/uso do sistema.
## 2. Superfícies funcionais
Furniture pode usar partículas, sons, sitting, sleeping, texturização automática baseada em material, lighting e estados alternáveis como open/closed. Estados podem responder a right-click e, na linha histórica documentada, a redstone.
## 3. State e authority
O servidor deve ser authority de placement, interação funcional, sitting/sleeping e qualquer storage/state persistente. Editor/model preview e biblioteca remota são apresentação/authoring; não devem conceder gameplay state sem validação.
## 4. Furniture entities e performance
A linha 0.3.x recebeu melhorias de performance para furniture items, editor e furniture entities. Em builds com muitos objetos customizados, testar custo de render/tick, colisão e chunk load em áreas densas.
## 5. Sable
A 0.3.2 corrigiu crashes ligados ao suporte Sable; a 0.3.3 corrige particle emitter ignorando rotação do bloco. Como o pack contém Sable, ambos são regression gates reais: móveis em sublevels/ships precisam preservar transformação, interação e partículas.
O JAR físico `immersive_furniture-neoforge-0.3.3+1.21.1.jar` embarca `META-INF/jarjar/sable-companion-common-1.21.1-1.5.0.jar`, mod `sablecompanion` 1.5.0. Pelo protocolo do catálogo, essa biblioteca/bridge é **dependência JarJar interna do host**, não um mod top-level separado. Atualizar Immersive Furniture pode alterar esse runtime interno mesmo sem surgir novo JAR na pasta `mods`.
## 6. Conteúdo remoto
Baixar furniture da biblioteca introduz conteúdo que não está representado apenas pelo JAR. Para reprodução do modpack, objetos necessários a quests/builds devem ser versionados/exportados de forma controlada; não depender de um item remoto que possa mudar ou desaparecer.
## 7. Riscos
1. Modelo/state custom não sincroniza entre clientes.
2. Furniture entity duplica/desaparece em chunk unload.
3. Sitting/sleeping conflita com pose/collision mods.
4. Sable transform quebra posição/rotação/interação.
5. Biblioteca remota muda conteúdo sem alteração de versão.
6. Partículas/som/lighting causam custo excessivo em builds densas.
7. Tratar `Sable Companion 1.5.0` embarcado como mod top-level ou ignorar seu version drift ao atualizar o host.
## 8. Boundary para quests/perks
Colocar ou usar um móvel só deve contar quando o server state correspondente for confirmado. Preview/editor/library download não são milestones de gameplay.
## 9. Matriz de testes
- [ ] Dedicated server inicia com 0.3.3.
- [ ] Furniture criada/importada persiste após restart.
- [ ] Toggle open/closed e redstone convergem em multiplayer.
- [ ] Sitting/sleeping não deixa pose presa.
- [ ] Particle emitter respeita rotação.
- [ ] Furniture em sublevel Sable mantém transform após unload/reload.
- [ ] Área com muitos móveis mantém custo aceitável.
Nenhum teste foi marcado como aprovado nesta auditoria documental.
## 10. Evidências e limite
CurseForge/Modrinth oficiais confirmam Release 0.3.3, editor, online library, partículas/sons/sitting/sleeping, material texturing, toggle states/lighting e changelog 0.3.1–0.3.3. A modlist/JAR inventory física confirma ainda `sable-companion-common-1.21.1-1.5.0.jar` embarcado em `META-INF/jarjar`. Schemas internos de furniture e storage não foram pinados ao binário nesta etapa.
