# kubejsarsnouveau — 1.3.2

> Fonte canônica desta importação: Notion — `Auditoria Mestre da Modlist — NeoForge 1.21.1`  
> Página-fonte: https://app.notion.com/p/3d369db9f0db81dda9ecf6338ba80244  
> Estado no momento da importação: `Instalado — Dossiê completo`  
> Autoridade física: `modlist(4).txt`, 595 mods top-level  
> Importado em: 2026-09-10

## Propriedades do registro

- **Mod:** kubejsarsnouveau
- **Arquivo JAR:** `kubejsarsnouveau-1.3.2.jar`
- **Versão 1.21.1:** `1.3.2`
- **Categoria:** Compat; Automação; Magia
- **Decisão:** Manter
- **Estado da pesquisa:** Verificado
- **Estado no pack na origem:** Instalado — Dossiê completo
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/kubejs-ars-nouveau
- **Função:** Integra KubeJS ao Ars Nouveau para criar/alterar recipes como Enchanting Apparatus, enchantment, Crush, Glyph, Caster Tome e Imbuement.
- **Dependências:** KubeJS 2101.7.2-build.374 + Ars Nouveau 5.13.1 estão fisicamente presentes. NeoForge 1.21.1; release é Client & Server.
- **Compatibilidade/Riscos:** Riscos: recipe duplication/stale reload, Source cost imbalance, invalid glyph references, loot inflation e API drift Ars/KubeJS. Scripts reais não foram auditados nesta ficha.
- **Sobreposição:** Complementa Ars Nouveau/KubeJS. Pode substituir/compor recipes Ars via IDs; não substitui spell engine, Source ou registries do provider.
- **Observações:** JAR físico `kubejsarsnouveau-1.3.2.jar`, mod id `kubejsarsnouveau`, runtime 1.3.2. Conteúdo material depende de scripts; addon sozinho não cria nova magia.
- **Procedência:** modlist.txt física atual de 08/09/2026 + KubeJS Wiki Ars Nouveau + CurseForge oficial KubeJS Ars Nouveau 1.3.2.
- **Histórico da decisão:** vazio.
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 09/09/2026 — corpo vazio corrigido; Apparatus/Imbuement/Glyph/Crush/Caster Tome recipe APIs, Source authority, reload e risks catalogados para 1.3.2.
- **Data da última decisão:** 2026-09-06

## Dossiê operacional — padrão Alex's Mobs

> 🔮 **ESCOPO CANÔNICO.** Runtime físico: `kubejsarsnouveau-1.3.2.jar`, mod id `kubejsarsnouveau`, versão `1.3.2`. O addon adiciona suporte KubeJS para **recipes Ars Nouveau**; não cria um segundo sistema de magia.

## 1. Recipe types documentados
A documentação KubeJS 1.21.1 confirma builders para:
- Enchanting Apparatus;
- enchantment recipes;
- Crush;
- Glyph recipe replacement;
- Caster Tome;
- Imbuement.
Esses recipes são definidos em `server_scripts` por `ServerEvents.recipes`.

## 2. Source cost e Ars authority
Recipes como Apparatus/Imbuement podem declarar Source cost. Ars Nouveau 5.13.1 continua authority de Source, machines, glyph validity e spell/tome behavior. O script descreve a recipe; não deve manter Source paralelo.

## 3. Glyph boundary
A própria documentação alerta que glyph recipe só funciona para glyph válido existente no tome e é adequada principalmente para substituir receita de glyph. Não tratar o addon como registry genérico de glyphs novos sem API/evidência adicional.

## 4. Caster Tomes
O builder de Caster Tome permite registrar tome/recipe com spell parts e metadata de apresentação. Spell parts continuam IDs do provider Ars. Um tome custom não deve ser catalogado como conteúdo ativo do pack sem script real que o registre.

## 5. Reload lifecycle
Recipes ficam no lifecycle de server data e precisam ser idempotentes em `/reload`. IDs estáveis são necessários para substituir recipes antigas sem duplicação. Alterar recipe em mundo existente deve manter compatibilidade com items/components existentes.

## 6. Runtime físico
Ars Nouveau 5.13.1 e KubeJS build.374 estão presentes. A release KubeJS Ars Nouveau 1.3.2 é NeoForge 1.21.1 Client & Server. Nenhuma incompatibilidade específica com o stack físico foi comprovada, mas API drift deve ser testado após updates.

## 7. Riscos
1. Recipe duplicate/stale após reload.
2. Source cost incorreto altera economia mágica.
3. Glyph recipe aponta para glyph inválido.
4. Apparatus ingredient set/keep-NBT diverge do esperado.
5. Crush loot gera duplicação excessiva.
6. API drift Ars/KubeJS quebra builders.

## 8. Boundary para projetos próprios
Black Arcana/RPG Skill Tree não devem inferir spell mastery a partir da existência de recipe/tome. Progressão deve usar evento/state causal do provider real. Recipe customization permanece layer de dados.

## 9. Matriz de testes
- [ ] Dedicated server inicia com 1.3.2 + Ars 5.13.1 + KubeJS build.374.
- [ ] `/reload` duas vezes não duplica recipes.
- [ ] Enchanting Apparatus conserva ingredients/reagent e Source cost.
- [ ] Imbuement aplica custo correto.
- [ ] Glyph replacement só afeta ID pretendido.
- [ ] Caster Tome resolve spell parts válidas.
- [ ] Crush respeita loot/chance sem dupe.
Nenhum teste foi marcado como aprovado nesta auditoria documental.

## 10. Evidências e limite
KubeJS Wiki e CurseForge oficiais documentam os builders acima e Release 1.3.2. Scripts reais não foram inspecionados; nenhuma recipe Ars custom é considerada ativa sem evidência no diretório KubeJS do pack.
