# Excalibur | Clarent Edition

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3d669db9f0db81348e40c3580cf1e264
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Tipo de conteúdo:** Resource Pack, Addon
- **Arquivo:** `Clarent_12110_1202+_v3.zip`
- **Versão 1.21.1:** v3
- **Data da exportação:** 2026-09-11

## Autoridade e limite físico na exportação

- O dossiê Notion registra `Clarent_12110_1202+_v3.zip` como fisicamente confirmado por captura da pasta Resource Packs do perfil em 08/09/2026.
- A busca atual na Biblioteca não recuperou diretamente esse ZIP/captura; portanto a presença/build do resource pack é preservada conforme a procedência do dossiê, sem converter a modlist JAR-centric em inventário de resource packs.
- O upstream identifica esta build como patch para Excalibur `1.21.10`, enquanto a base física do perfil é `Excalibur_V26.1_01.zip`. Essa combinação não é confirmada oficialmente e permanece fail-closed até QA visual.

## Propriedades do banco

- **Mod:** Excalibur | Clarent Edition
- **Arquivo JAR:** `Clarent_12110_1202+_v3.zip`
- **Tipo de conteúdo:** Resource Pack, Addon
- **Versão 1.21.1:** v3
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Visual, Compat
- **Função:** Patch/backport visual do Excalibur que aplica correções e compatibilidades entre versões; a build instalada 12110_1202+_v3 foi feita para Excalibur 1.21.10.
- **Dependências:** Excalibur base. Upstream exige Clarent diretamente acima do Excalibur e addons específicos acima do Clarent. Perfil atual usa Excalibur V26.1_01, combinação não confirmada para esta build 12110.
- **Sobreposição:** Camada transversal sobre Excalibur; outros support packs devem ficar acima de Clarent. Sobreposição com Excalibur é intencional, mas o base V26.1_01 cria risco de regressão por drift.
- **Compatibilidade/Riscos:** DRIFT CRÍTICO: Clarent 12110 v3 é documentado para Excalibur 1.21.10, mas o pack usa Excalibur V26.1_01. Pode reverter/mascarar assets novos. Não assumir incompatibilidade definitiva nem compatibilidade sem QA.
- **Observações:** Arquivo instalado `Clarent_12110_1202+_v3.zip`: 12110 = Excalibur 1.21.10, 1202+ = faixa Minecraft, v3 = build. Minecraft 1.21.1 está na faixa suportada; o base pack V26.1_01 é o ponto não confirmado.
- **Procedência:** Captura CurseForge do perfil RPG em 08/09/2026 + página canônica Excalibur instalada + CurseForge oficial Clarent v3 e Excalibur V26.1_01.
- **Fonte:** https://www.curseforge.com/minecraft/texture-packs/clarent-excalibur-patch
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 11/09/2026 — Clarent v3 catalogado; load order explícito, patch/backport scope e drift crítico Excalibur 1.21.10 → V26.1_01 registrados; QA visual pendente.
- **Histórico da decisão:**

# Dossiê operacional — padrão Alex's Mobs

> **Resource pack físico confirmado no dossiê de origem:** `Clarent_12110_1202+_v3.zip`, build `v3`. O upstream identifica esta variante como patch para **Excalibur 1.21.10** e Minecraft 1.20.2–1.21.11. O perfil atual, porém, usa **Excalibur V26.1_01**; essa combinação não é confirmada oficialmente e permanece um gate de compatibilidade visual.

## 1. Papel e authority
Excalibur | Clarent Edition é um patch/backport não oficial do Excalibur. Seu objetivo é preservar a experiência visual do Excalibur em várias versões de Minecraft, aplicar correções e oferecer pequenas compatibilidades de mods.
Excalibur continua o resource pack base. Clarent deve ser tratado como **camada de override**, não como mod de gameplay nem como substituto automático de qualquer versão futura do Excalibur.

## 2. Build instalada e versionamento
Arquivo instalado: `Clarent_12110_1202+_v3.zip`.
O esquema oficial de nomes define:
- `12110` = versão do Excalibur suportada, **1.21.10**;
- `1202+` = faixa de Minecraft suportada, de 1.20.2 em diante dentro da linha publicada;
- `v3` = build do Clarent.
Para Minecraft 1.21.1, a faixa de Minecraft é compatível; o risco está no **base pack**, porque o Excalibur instalado já é `V26.1_01`.

## 3. Divergência com o Excalibur físico
O perfil atual usa `Excalibur_V26.1_01.zip`, release mais nova que `Excalibur_V1.21.10`. O upstream do Clarent não documenta `V26.1_01` como base suportada para `12110_1202+_v3`.
Não afirmar incompatibilidade binária — resource packs não têm esse tipo de linkage — mas também não assumir compatibilidade visual. Clarent pode sobrescrever com assets mais antigos, reintroduzir arte anterior ou neutralizar mudanças feitas em Excalibur V26.1_01.

## 4. Load order obrigatório
O upstream publica ordem recomendada explícita:
1. Excalibur;
2. Clarent diretamente acima do Excalibur;
3. outros addons opcionais acima do Clarent.
Clarent deve carregar **diretamente acima** do Excalibur. Support packs específicos, como os demais addons Excalibur deste catálogo, devem ficar acima de Clarent quando precisarem vencer seus overrides.

## 5. Cobertura confirmada
Clarent documenta:
- backports e correções visuais;
- melhorias de wool/banner blending;
- compatibilidade com Excalibur Fresh Animations;
- suporte/patches para diversos mods, incluindo Better Mod List, Cherished Worlds, Dynamic Trees, Eating Animation, Extended Drawers, Fabric Skyboxes, Falling Leaves, Guard Villagers, Inventory Management, Jade, LambdaBetterGrass, RP Renames, Subtle Effects e Xaero's maps.
O upstream alerta que mod support pode variar por versão; não tratar essa lista como garantia universal no ambiente 1.21.1.

## 6. Client e resource reload
É camada visual client-side. Ativar, remover ou mudar prioridade provoca resource reload e pode trocar assets imediatamente, sem modificar save, entities, inventories ou regras de mods.
Como Clarent é amplo, um reload pode afetar muitos namespaces visuais de uma vez; regressões precisam ser isoladas por prioridade de pack.

## 7. Sobreposição
Clarent é uma camada transversal e pode tocar assets que também são alterados por support packs específicos. A regra operacional é manter packs específicos **acima de Clarent** quando ambos modificarem o mesmo caminho.
A sobreposição com o Excalibur base é intencional; a sobreposição com Excalibur V26.1_01 é potencialmente problemática por version drift.

## 8. Riscos
1. Clarent 12110 reverter assets novos do Excalibur V26.1_01.
2. Patch antigo mascarar fixes posteriores do Excalibur.
3. Ordem incorreta colocar Clarent abaixo do Excalibur e neutralizá-lo.
4. Addon específico abaixo de Clarent perder seus overrides.
5. Mod support embutido variar por versão e gerar assets incompletos.
6. Resource reload deixar mistura visual até recarregar o cliente/mundo.

## 9. Matriz de testes
- [ ] Confirmar ordem: Excalibur → Clarent → addons específicos acima.
- [ ] Comparar blocos/itens alterados pelo Excalibur V26.1_01 com Clarent on/off para detectar regressões.
- [ ] Testar wool/banner blending e fixes visuais documentados.
- [ ] Verificar compatibilidade com Excalibur Fresh Animations quando ativo.
- [ ] Inspecionar Xaero/Jade e outros mod-supports efetivamente presentes.
- [ ] Resource reload sem missing assets.
- [ ] Se regressões forem encontradas, testar sem Clarent antes de alterar outros packs.

Nenhum teste foi marcado como aprovado.

## 10. Evidências e limite
- captura CurseForge do perfil: `Clarent_12110_1202+_v3.zip` instalado;
- página canônica Excalibur no perfil: `Excalibur_V26.1_01.zip` instalado;
- CurseForge oficial Clarent: patch para Excalibur 1.21.10, faixa Minecraft 1.20.2–1.21.11, load order explícito e lista de compats;
- CurseForge oficial Excalibur: V26.1_01 é release posterior e suporta Minecraft 1.21.1.
Não há confirmação oficial de Clarent 12110 v3 sobre Excalibur V26.1_01; esse ponto permanece fail-closed.

> Boundary canônico: **Clarent 12110 v3 foi feito para Excalibur 1.21.10; o uso sobre Excalibur V26.1_01 exige QA visual antes de ser considerado compatível**.
