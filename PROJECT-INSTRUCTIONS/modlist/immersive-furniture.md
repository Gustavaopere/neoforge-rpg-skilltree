# Immersive Furniture

> Fonte canônica desta importação: Notion — `Auditoria Mestre da Modlist — NeoForge 1.21.1`  
> Página-fonte: https://app.notion.com/p/3d369db9f0db81cc9d30e0c002bca0b3  
> Estado no momento da exportação: `Instalado — Dossiê completo`  
> Autoridade física no momento da exportação: modlist física mais recente, 595 mods  
> Exportado em: 2026-09-09

## Propriedades do registro

- **Mod:** Immersive Furniture
- **Arquivo JAR:** `immersive_furniture-neoforge-0.3.3+1.21.1.jar`
- **Versão 1.21.1:** `0.3.3+1.21.1`
- **Categoria:** QoL; Visual
- **Decisão:** Manter
- **Estado da pesquisa:** Verificado
- **Estado no pack na origem:** Instalado — Dossiê completo
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/immersive-furniture
- **Função:** Sistema de furniture customizada com editor in-game e biblioteca online; suporta partículas, sons, sitting/sleeping, material-based texturing, estados alternáveis e lighting.
- **Dependências:** NeoForge 1.21.1. Client & Server. Sable é integração relevante no pack e teve crash fix na linha 0.3.2; não foi tratado como hard dependency universal.
- **Compatibilidade/Riscos:** Riscos: furniture entity/state desync, pose/collision, conteúdo remoto não reprodutível, custo em builds densas e transforms em Sable. 0.3.2 corrige crashes Sable; 0.3.3 corrige particle emitter rotation.
- **Sobreposição:** Sobreposição temática com furniture/decor mods, mas editor/importação de objetos customizados e library remota são superfícies próprias.
- **Observações:** JAR físico `immersive_furniture-neoforge-0.3.3+1.21.1.jar`, mod id `immersive_furniture`, runtime 0.3.3+1.21.1. Release NeoForge 1.21.1 de 02/08/2026.
- **Procedência:** modlist.txt física atual de 08/09/2026 + CurseForge/Modrinth oficiais Immersive Furniture 0.3.3 e changelog 0.3.1–0.3.3.
- **Histórico da decisão:**
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 09/09/2026 — corpo vazio corrigido; editor, library, furniture state/entities, sitting/sleeping, Sable regressions, lifecycle e testes catalogados para 0.3.3.
- **Data da última decisão:** 2026-09-06

## Dossiê operacional — padrão Alex's Mobs

> 🪑 **ESCOPO CANÔNICO.** Runtime físico: `immersive_furniture-neoforge-0.3.3+1.21.1.jar`, mod id `immersive_furniture`, versão `0.3.3+1.21.1`. É um sistema Client & Server para criar, importar e usar furniture customizada, não apenas um pack estático de modelos.

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

## 6. Conteúdo remoto
Baixar furniture da biblioteca introduz conteúdo que não está representado apenas pelo JAR. Para reprodução do modpack, objetos necessários a quests/builds devem ser versionados/exportados de forma controlada; não depender de um item remoto que possa mudar ou desaparecer.

## 7. Riscos
1. Modelo/state custom não sincroniza entre clientes.
2. Furniture entity duplica/desaparece em chunk unload.
3. Sitting/sleeping conflita com pose/collision mods.
4. Sable transform quebra posição/rotação/interação.
5. Biblioteca remota muda conteúdo sem alteração de versão.
6. Partículas/som/lighting causam custo excessivo em builds densas.

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
CurseForge/Modrinth oficiais confirmam Release 0.3.3, editor, online library, partículas/sons/sitting/sleeping, material texturing, toggle states/lighting e changelog 0.3.1–0.3.3. Schemas internos de furniture e storage não foram pinados ao binário nesta etapa.
