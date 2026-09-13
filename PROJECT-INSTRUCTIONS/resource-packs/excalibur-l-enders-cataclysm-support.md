# Excalibur | L_Ender's Cataclysm Support

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3d669db9f0db8130ac36f5504ad58c21
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Tipo de conteúdo:** Resource Pack, Addon
- **Arquivo:** `Excalibur Cataclysm 1.0 (Neoforge).zip`
- **Versão 1.21.1:** 1.0
- **Data da exportação:** 2026-09-11

## Autoridade e limite físico na exportação

- O dossiê Notion registra `Excalibur Cataclysm 1.0 (Neoforge).zip` como fisicamente confirmado por captura da pasta Resource Packs do perfil em 08/09/2026.
- A modlist física de 08/09/2026 confirma `L_Ender's Cataclysm 1.21.1-3.33.jar`, mod id `cataclysm`, runtime `3.33`.
- O upstream do support v1.0 declara cobertura de todas as entities/items/blocks/spawn eggs do escopo da release, além de random variants e ícones 2D para weapons 3D; isso é preservado como claim oficial, não inventário independente do ZIP.
- A ficha cobre o mod base Cataclysm. Cataclysm: Spellbooks e outros addons permanecem namespaces/projetos separados e não são considerados cobertos automaticamente.

## Propriedades do banco

- **Mod:** Excalibur | L_Ender's Cataclysm Support
- **Arquivo JAR:** `Excalibur Cataclysm 1.0 (Neoforge).zip`
- **Tipo de conteúdo:** Resource Pack, Addon
- **Versão 1.21.1:** 1.0
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Visual, Compat, Mobs
- **Função:** Compatibility resource pack 16x que retexturiza L_Ender's Cataclysm para Excalibur, cobrindo entities, items, blocks e spawn eggs e ajustando apresentação de weapons 3D no inventário.
- **Dependências:** Uso visual pretendido: Excalibur + L_Ender's Cataclysm. Stack físico atual: Cataclysm runtime 3.33. Não é dependência de gameplay/servidor.
- **Sobreposição:** Cobre o mod base Cataclysm. Não cobre automaticamente Cataclysm: Spellbooks nem addons; outros packs que toquem os mesmos asset paths podem prevalecer conforme prioridade.
- **Compatibilidade/Riscos:** Risco de drift entre o support pack 1.0 de 01/06/2026 e Cataclysm 3.33; colisão com outros retextures, random-variant assets e divergência entre ícone 2D e modelo 3D.
- **Observações:** Arquivo instalado `Excalibur Cataclysm 1.0 (Neoforge).zip`. Upstream declara retexture de todas as entities/items/blocks/spawn eggs, random variants e sprites 2D para ícones de weapons 3D.
- **Procedência:** Captura CurseForge do perfil RPG em 08/09/2026 + modlist física atual + CurseForge oficial da release 1.0 NeoForge para Minecraft 1.21.1.
- **Fonte:** https://www.curseforge.com/minecraft/texture-packs/excalibur-l-ender-s-cataclysm
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 11/09/2026 — dossiê visual reconstruído; Cataclysm 3.33, cobertura oficial, random variants, ícones 2D, load order, lifecycle, riscos e QA catalogados.
- **Histórico da decisão:**

# Dossiê operacional — padrão Alex's Mobs

> **Resource pack físico confirmado no dossiê de origem:** `Excalibur Cataclysm 1.0 (Neoforge).zip`, versão `1.0`, Release para Minecraft 1.21.1. É um compatibility pack 16x para L_Ender's Cataclysm.

## 1. Papel e authority
Excalibur | L_Ender's Cataclysm Support retexturiza o conteúdo de **L_Ender's Cataclysm** para o estilo Excalibur. Cataclysm continua authority de bosses, mobs, structures, weapons, armor, blocks, particles, AI, damage e loot.
O pack é puramente visual e não cria um segundo sistema de modelos/combate.

## 2. Cobertura confirmada
O upstream declara que a release 1.0 retexturiza **todas as entidades, itens, blocos e spawn eggs** do escopo coberto pelo pack.
Também converte weapons que usam modelos 3D em **sprites 2D para o ícone de inventário**. Isso altera apenas apresentação do item em GUI/inventory; o modelo de uso/entidade continua pertencendo ao mod.

## 3. Variantes visuais
O projeto inclui **random entity variants** e altera levemente cores de shulkers para manter coerência visual com a lógica do pack.
Randomização de textura não deve ser confundida com variante de entidade ou stat diferente; gameplay permanece idêntico.

## 4. Stack físico atual
A modlist contém `L_Ender's Cataclysm 1.21.1-3.33.jar`, mod id `cataclysm`, runtime `3.33`. O support pack instalado é 1.0 NeoForge, publicado em 01/06/2026.
A página oficial não fixa uma versão mínima/máxima de Cataclysm além do alvo do projeto; portanto a compatibilidade exata com Cataclysm 3.33 precisa de QA visual, especialmente para conteúdo adicionado depois do lançamento do pack.

## 5. Load order
Para aplicar os overrides de Cataclysm e manter o tema Excalibur, o support pack deve ter prioridade acima do Excalibur base. Packs alternativos que também modifiquem Cataclysm podem substituir assets individualmente conforme ordem.

## 6. Client e resource reload
Models/textures/icons são client-side. Resource reload deve atualizar aparência sem alterar health, boss phase, projectile behavior, drops ou save state.
Random variants também devem recarregar sem missing texture e sem trocar causalidade/identidade de entidades.

## 7. Sobreposição
Há outros packs do pack/modpack que podem tocar Cataclysm, inclusive retextures 2D/3D e compats de spellbooks. Sobreposição deve ser avaliada por namespace/asset path: este pack cobre o **mod base L_Ender's Cataclysm**, não Cataclysm: Spellbooks nem integrações de terceiros automaticamente.

## 8. Riscos
1. Cataclysm 3.33 possuir assets posteriores à release 1.0.
2. Pack alternativo sobrescrever entity/item/block assets.
3. Ícones 2D divergir do modelo 3D usado em mão/mundo e parecer item diferente.
4. Random entity variants falharem ou usarem fallback.
5. Spawn egg/icon cache não atualizar após resource reload.
6. Usuário interpretar mudança visual como alteração de hitbox/combate.

## 9. Matriz de testes
- [ ] Amostra de bosses e mobs principais sem missing texture.
- [ ] Items e blocks principais exibem estilo Excalibur.
- [ ] Spawn eggs retexturizados.
- [ ] Weapons 3D exibem ícones 2D corretos no inventário e continuam com modelo esperado em uso.
- [ ] Random entity variants aparecem sem erro.
- [ ] Shulkers usam paleta esperada.
- [ ] Resource reload/relog mantém os overrides.
- [ ] Verificar assets novos da Cataclysm 3.33 não cobertos pelo pack 1.0.

Nenhum teste foi marcado como aprovado.

## 10. Evidências e limite
- captura CurseForge do perfil: `Excalibur Cataclysm 1.0 (Neoforge).zip`;
- modlist física: L_Ender's Cataclysm runtime 3.33;
- CurseForge oficial: release 1.0 NeoForge, cobertura de entities/items/blocks/spawn eggs, ícones 2D para weapons 3D, random variants e ajuste de shulkers.
Não foi auditado o manifesto interno do ZIP; o claim de cobertura é reproduzido como declaração oficial do projeto.

> Boundary canônico: **Cataclysm controla todo gameplay; o support pack controla exclusivamente os assets visuais que substitui**.
