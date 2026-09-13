# Excalibur | Fresh Animations Patch

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3d669db9f0db81bc9fbffc314ba0b5f7
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Tipo de conteúdo:** Resource Pack, Addon
- **Arquivo:** `EFA_1.10.4_Hotfix 3.zip`
- **Versão 1.21.1:** 1.10.4 Hotfix 3
- **Data da exportação:** 2026-09-11

## Autoridade e limite físico na exportação

- O dossiê Notion registra `EFA_1.10.4_Hotfix 3.zip` como fisicamente confirmado por captura da pasta Resource Packs do perfil em 08/09/2026.
- A modlist física de 08/09/2026 confirma Entity Texture Features `7.2.1` e Entity Model Features `3.3.5`, atendendo a superfície ETF/EMF citada pelo upstream.
- O stack visual físico usa Excalibur `V26.1_01` e Clarent `12110_1202+_v3`, enquanto o upstream atual do patch recomenda Excalibur `1.21.11` para Minecraft 1.21.x e Clarent nas linhas aplicáveis. A combinação física permanece sujeita a QA.
- Epic Fight `21.17.3.1` está fisicamente presente e é listado explicitamente como incompatível pelo upstream deste patch; o conflito permanece aberto e não é resolvido automaticamente por prioridade de resource pack.

## Propriedades do banco

- **Mod:** Excalibur | Fresh Animations Patch
- **Arquivo JAR:** `EFA_1.10.4_Hotfix 3.zip`
- **Tipo de conteúdo:** Resource Pack, Addon
- **Versão 1.21.1:** 1.10.4 Hotfix 3
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Visual, Compat
- **Função:** Patch visual que torna Excalibur compatível com Fresh Animations/Freshly Modded, ajustando textures/models e regras de prioridade para preservar animações com a estética Excalibur.
- **Dependências:** Upstream: Fresh Animations obrigatório; para MC 1.21.x recomenda FA 1.10.4. Requer ETF+EMF ou OptiFine; stack físico contém ETF 7.2.1 + EMF 3.3.5. Também requer Excalibur; Clarent é exigido pelo upstream para 1.21.x exceto 1.21.11.
- **Sobreposição:** Sobreposição direta com Fresh Animations, Clarent e Excalibur é intencional. Epic Fight é incompatibilidade explícita. Fresh Player Animation Extension deve ficar abaixo do Excalibur segundo o upstream para não sobrescrever textura de leggings.
- **Compatibilidade/Riscos:** CONFLITO OFICIAL: Epic Fight é listado como incompatível. Stack atual também diverge da recomendação: usa Excalibur V26.1_01 + Clarent 12110 v3, enquanto upstream recomenda Excalibur 1.21.11 + Clarent para MC 1.21.x. Load order incorreto pode sobrescrever models/leggings/player assets.
- **Observações:** Arquivo instalado `EFA_1.10.4_Hotfix 3.zip`, catalogado como `1.10.4 Hotfix 3`. Para 1.21.x o upstream mantém FA 1.10.4; OptiFine é desaconselhado pelo autor em favor de ETF/EMF.
- **Procedência:** Captura CurseForge do perfil RPG em 08/09/2026 + modlist física ETF/EMF + CurseForge oficial Excalibur Fresh Animations Patch 1.10.4 Hotfix 3 e load order atual.
- **Fonte:** https://www.curseforge.com/minecraft/texture-packs/excalibur-fresh-animations-patch
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 11/09/2026 — dossiê Fresh Animations Patch reconstruído; 1.10.4 Hotfix 3, load order oficial, ETF/EMF, Clarent/Excalibur drift, Epic Fight incompatível e QA catalogados.
- **Histórico da decisão:**

# Dossiê operacional — padrão Alex's Mobs

> **Resource pack físico confirmado no dossiê de origem:** `EFA_1.10.4_Hotfix 3.zip`, versão `1.10.4 Hotfix 3`. O projeto é o patch oficial de compatibilidade Excalibur ↔ Fresh Animations/Freshly Modded para a linha Minecraft 1.21.x.

## 1. Papel e authority
O patch coordena models/textures necessários para manter a estética Excalibur enquanto Fresh Animations controla animações/model definitions. Ele não altera AI, hitboxes, attributes ou gameplay das entidades.

## 2. Requisitos publicados
Para Minecraft 1.21.x, o upstream recomenda **Fresh Animations 1.10.4**. Também exige ETF+EMF ou OptiFine; o autor recomenda ETF/EMF. O pack físico contém ETF `7.2.1` e EMF `3.3.5`, portanto essa superfície está presente.

## 3. Load order oficial — top para bottom
1. Excalibur | Fresh Animations Patch;
2. Freshly Modded Lib, opcional;
3. Freshly Modded Resource Pack, opcional;
4. Fresh Animations, obrigatório;
5. Excalibur Mod Support packs;
6. Excalibur | Clarent Edition para 1.21.x exceto 1.21.11;
7. Excalibur base;
8. Fresh Player Animation Extension deve ficar abaixo do Excalibur quando usado, para não sobrescrever a textura de leggings.
Essa ordem é parte do contract funcional visual do patch.

## 4. Drift do stack físico
O upstream atual recomenda **Excalibur 1.21.11** para Minecraft 1.21.x. O perfil usa **Excalibur V26.1_01** e Clarent `12110_1202+_v3`. A combinação pode funcionar, mas não é a base recomendada pelo patch atual e precisa de QA.

## 5. Incompatibilidade com Epic Fight
O upstream lista **Epic Fight** explicitamente entre incompatibilidades. Epic Fight está fisicamente presente no pack e o resource pack `Excalibur | Epic Fight Support` também está ativo. Isso é um conflito publicado, não inferido.
Não há remoção automática: a decisão deve vir de teste real e curadoria do stack.

## 6. Outras superfícies de overlap
Fresh Animations: Extensions, Player Extension e outros packs de mob/model podem tocar models/textures próximos. A ordem precisa respeitar a documentação de cada addon; não classificar todos como incompatíveis sem evidência específica.

## 7. Client e resource reload
Todo o sistema é client-side. Resource reload/relog precisa reconstruir models e rules sem missing texture, model recursion ou entidade invisível. O servidor continua authority de entity state.

## 8. Riscos
1. Epic Fight incompatível com o patch.
2. Excalibur V26.1_01 divergir da base 1.21.11 recomendada.
3. Clarent 12110 divergir do Excalibur V26.1_01.
4. Load order quebrar model/texture precedence.
5. Player Extension sobrescrever leggings se colocado acima do Excalibur.
6. Outro Fresh Animations addon disputar o mesmo model.
7. ETF/EMF rule falhar após reload.

## 9. Matriz de testes
- [ ] Confirmar ordem top→bottom conforme upstream.
- [ ] Confirmar ETF 7.2.1 + EMF 3.3.5 ativos.
- [ ] Testar mobs vanilla principais com Fresh Animations.
- [ ] Testar Epic Fight ativo e isolado para reproduzir/descartar conflito no stack real.
- [ ] Comparar Clarent/Excalibur V26.1_01 com a recomendação upstream.
- [ ] Verificar Player Extension e leggings.
- [ ] Resource reload/relog sem entidades invisíveis ou models quebrados.

Nenhum teste foi marcado como aprovado.

## 10. Evidências e limite
CurseForge oficial confirma 1.10.4 Hotfix 3 para a linha 1.21, a ordem recomendada, requisitos ETF/EMF ou OptiFine e a incompatibilidade com Epic Fight. O comportamento real da combinação V26.1_01 + Clarent 12110 permanece fail-closed até QA.

> Boundary canônico: **há incompatibilidade oficial com Epic Fight e drift da base Excalibur recomendada; o stack não deve ser declarado validado sem teste visual**.
