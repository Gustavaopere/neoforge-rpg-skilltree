# Entity Texture Features

## Propriedades do registro

- **Mod:** Entity Texture Features
- **Arquivo JAR:** `entity_texture_features-7.2.1-1.21-neoforge.jar`
- **Versão 1.21.1:** `7.2.1`
- **Categoria:** Visual
- **Função:** Client-side texture framework para random/custom/emissive/blinking entity textures e player skin features usando formatos OptiFine/ETF e regras `.properties`.
- **Dependências:** Cliente NeoForge 1.21.1. Runtime local integra com Entity Model Features 3.3.5 e Entity Sound Features 0.8.2; Iris 1.8.14-beta.1 e Sodium 0.8.13 estão presentes e são superfícies de render/shader relevantes.
- **Estado no pack:** Integrado ao Github
- **Estado da pesquisa:** Verificado
- **Compatibilidade/Riscos:** Riscos em resource-pack precedence, shader emissives/PBR, random property state, player-skin transparency/cosmetics, render-layer overrides e modded entities com renderers não vanilla. EMF é compatível e usa ETF; OptiFine/OptiFabric não devem compor o mesmo stack. Shader support varia por shader.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/entity-texture-features-fabric
- **Procedência:** modlist.txt física atual de 21/09/2026 — 587 entradas top-level incluindo o modloader — confirma `entity_texture_features-7.2.1-1.21-neoforge.jar`, mod id `entity_texture_features`, runtime 7.2.1 e SHA-1 `908d09263709193b918fdc9ca0d79c9d90c4a531`. CurseForge oficial revalidado em 21/09/2026 mantém 7.2.1 para 1.21.1; o changelog pontual de crash em 26.1 não é projetado sobre o runtime 1.21.1.
- **Observações:** Runtime físico 7.2.1. A publicação oficial 7.2.1 continua sendo a release aplicável a 1.21.1; seu changelog público pontual menciona correção de crash em 26.1. Esse fix não é tratado como mudança comportamental comprovada do artefato 1.21.1. Config oficial: `config/entity_texture_features.json`; EMF permanece owner de CEM/modelos.
- **Atualização/Status:** REAUDITADO EM 21/09/2026 — lote físico #255: `entity_texture_features-7.2.1-1.21-neoforge.jar` / runtime `7.2.1` reconfirmados na modlist física atual de 587 entradas top-level incluindo o modloader. CurseForge oficial continua com 7.2.1 como release aplicável a NeoForge 1.21.1.
- **Decisão:** Sem decisão
- **Sobreposição:** ETF é authority da variação/textura client-side. EMF controla CEM/modelos e ESF sons. Resource packs e shaders podem sobrepor apresentação; o entity provider continua authority do state gameplay.
- **Data da última decisão:** 2026-08-26

# Dossiê operacional — padrão Alex's Mobs
> **Runtime físico confirmado:** `entity_texture_features-7.2.1-1.21-neoforge.jar` · mod id `entity_texture_features` · versão `7.2.1` · NeoForge 1.21.1 · **client-side**.
## 1. Papel no modpack
Entity Texture Features (ETF) implementa variação e efeitos de textura para entities/player skins, com forte compatibilidade com formatos OptiFine e extensões próprias. É a base de textura do stack ETF/EMF/ESF.
## 2. Random/custom textures
ETF suporta random/custom entity textures via `.properties`, incluindo regras OptiFine e properties adicionais próprias. Resource packs podem armazenar variants em diretórios ETF, OptiFine ou, conforme configuração, junto das texturas vanilla/modded.
A ausência de regra correspondente deve cair no fallback documentado; não transformar missing variant em erro de gameplay.
## 3. Properties
A documentação inclui condições como biome/name/height e extensões ETF como `blocks`, `teams`, `speed`, `jumpStrength`, `maxHealth`, `hiddenGene`, `playerCreated`, `distanceFromPlayer`, `creeperCharged`, `statusEffect` e outras.
Algumas properties são avaliadas apenas no spawn/load por padrão; outras podem ser atualizadas em runtime. A config de update restrictions altera essa semântica e precisa ser considerada ao criar packs.
## 4. Emissive textures
ETF suporta emissive/glowing textures em formato OptiFine. Shaders podem possuir sua própria emissive/PBR pipeline; a documentação alerta que compatibilidade depende do shader e que duas emissive implementations podem competir.
No pack atual, **Iris 1.8.14-beta.1** está presente, então emissives precisam de QA com o shader profile real.
## 5. Blinking
Resource packs podem adicionar blink textures e ajustar frequência/duração. Sleeping/blind states também podem influenciar a apresentação quando assets adequados existem.
Blinking é exclusivamente visual e não representa sleep/vision state autoritativo.
## 6. Player skin features
ETF oferece features opcionais em skins de jogador, incluindo emissive, blinking, enchanted/transparency e outros cosmetics conforme o formato de skin. O projeto inclui opções para limitar abuso/visibilidade em contextos competitivos.
Skin cosmetics não devem criar armor, hitbox ou gameplay attributes.
## 7. Render properties
Properties não numeradas podem alterar apresentação, incluindo brightness override, suppression de certas ambient particles, hidden model parts e render-layer override como translucent/outline.
Esses overrides podem interagir fortemente com shaders e devem ser tratados como render configuration, não entity state.
## 8. Modded entities
ETF suporta entidades modded quando elas usam o caminho normal/compatível de rendering de LivingEntity. Renderers customizados podem não participar da pipeline.
Antes de criar regra para um mod, validar texture ResourceLocation e renderer real em vez de assumir path vanilla.
## 9. Configuração
O caminho oficial é:
`config/entity_texture_features.json`
A configuração também é acessível por GUI do stack Entity Features quando disponível. Alterações de config podem mudar selection/update/render behavior sem modificar resource pack.
## 10. EMF
EMF 3.3.5 está instalado e **depende do ETF** para parte de suas capacidades, incluindo random model/texture integration e config. ETF não fornece Custom Entity Models; esse ownership é do EMF.
## 11. ESF
ESF 0.8.2 usa o mesmo ecossistema de `.properties` e pode correlacionar sound/model/texture suffixes/rules. ETF continua responsável pela textura; ESF não deve escolher gameplay variant.
## 12. Iris / Sodium
Iris 1.8.14-beta.1 e Sodium 0.8.13 estão no pack. O projeto ETF declara compatibilidade com Sodium e compatibilidade de shaders variável conforme shader. Regressões devem ser reproduzidas com o shader/resource-pack real, não apenas em vanilla rendering.
## 13. Client / Server
Toda seleção de texture, skin feature, emissive, blink e render override é client-side. O servidor envia entity state normal; clientes podem renderizar variants diferentes sem alterar gameplay.
## 14. Lifecycle
Validar:
- resource-pack load/reload;
- config reload/restart;
- entity spawn/load;
- name/team/status change para properties atualizáveis;
- player skin refresh;
- dimension change;
- reconnect;
- shader toggle/reload;
- EMF/ESF update;
- texture resource missing/corrupt.
## 15. Multiplayer
Random texture selection pode depender de dados disponíveis no cliente, mas não pode alterar entity identity/state no servidor. Player skin features devem permanecer apresentação local.
## 16. Riscos
1. `.properties` inválido ou path incorreto;
2. variant gap/arquivo ausente;
3. update restrictions produzirem texture stale;
4. emissive duplo com shader;
5. PBR/render-layer conflict;
6. player skin transparency/cosmetic abuse;
7. modded renderer não passar pela pipeline ETF;
8. resource-pack precedence substituir variant;
9. EMF/ETF version mismatch;
10. ESF property cross-reference stale;
11. resource reload manter cache antigo;
12. confundir texture variant com entity gameplay variant.
## 17. Matriz de testes
1. ETF 7.2.1 sem EMF/ESF em resource pack simples.
2. Random texture por biome/name/team.
3. Property atualizável mudando em runtime.
4. Emissive com shader Iris atual.
5. Blink texture.
6. Player skin feature em first/third person.
7. Modded entity com renderer compatível.
8. EMF random model + ETF texture variant.
9. ESF sound variant correlacionado ao textureSuffix.
10. Resource reload e reconnect.
11. Shader on/off e mudança de resource pack.
**Esta catalogação não afirma que esses testes foram executados.**
## 18. Evidências
- modlist física atual: JAR/mod id/version + EMF/ESF/Iris/Sodium atuais;
- CurseForge/GitHub oficiais ETF: random/custom/emissive textures, player skin features, blinking, config e compatibilities;
- RANDOM_GUIDE/changelog oficial: properties, directories, render overrides e update semantics.
> **Boundary canônico:** ETF controla **textura/apresentação client-side**. Modelos pertencem ao EMF; sons ao ESF; gameplay permanece no entity provider.
