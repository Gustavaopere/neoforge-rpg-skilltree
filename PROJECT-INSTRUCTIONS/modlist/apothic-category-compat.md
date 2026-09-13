# Apothic Category Compat

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db811aa55af05c28982cde
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist.txt` — 595 mods top-level
- **Data da exportação:** 2026-09-09

## Propriedades do banco

- **Mod:** Apothic Category Compat
- **Arquivo JAR:** `apothic_compat-2.0.2.jar`
- **Versão 1.21.1:** 2.0.2
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Compat, RPG
- **Função:** Compat server-side/data-map para Apotheosis 8.x que corrige loot categories de armas modded que o auto-categorizer não detecta corretamente. Na build 2.0.2, força como `bow`: Alex's Caves Raygun/Dreadbow; Alex's Mobs Hemolymph Blaster/Blood Sprayer; Born in Chaos Pumpkin Pistol; Cataclysm Cursed Bow/Wrath of the Desert/Void Assault Shoulder Weapon/Wither Assault Shoulder Weapon/Laser Gatling; Undergarden Slingshot; Twilight Forest Block and Chain/Cube of Annihilation. Mantém affix blacklist.
- **Dependências:** Apotheosis 8.8.0 atende o requisito da linha NeoForge 1.21.1/2.0.2. Overrides só têm efeito quando o mod-alvo e o registry id esperado existem.
- **Sobreposição:** Complementar a Apothic Compats. Category Compat corrige categorização de itens para que affixes/gem sockets usem pools corretos; Apothic Compats adiciona integrações mais amplas como affixed loot, gear sets, affixes, gems/invaders/categorias. Não são duplicatas.
- **Compatibilidade/Riscos:** No pack atual existem Alex's Caves Continued, Alex's Mobs Continued, Born in Chaos e L_Ender's Cataclysm, portanto esses overrides são potencialmente ativos; Undergarden e Twilight Forest não foram localizados top-level e suas regras ficam dormentes. Como ports Continued podem preservar mod IDs mas não se deve inferir item IDs sem runtime, testar cada item alvo. Affix blacklist bloqueia rolls futuros; itens já existentes mantêm affixes. 1.21.1 usa data map e datapack reload para categories; blacklist pode ser recarregada por `/acc reload`.
- **Observações:** Não importar features 1.20.1 removidas em Apotheosis 8 (universal speed/damage rule, sword-vs-heavy split, TOML category overrides) para esta build. Em 2.0.2 NeoForge categories são data map. Testar port IDs de Alex's Caves/Mobs Continued antes de declarar override efetivo.
- **Procedência:** modlist.txt física atual de 08/09/2026 + CurseForge oficial Apothic Category Compat 2.0.2 + dossiê operacional existente.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/apothic-category-compat
- **Atualização/Status:** PADRÃO ALEX'S MOBS REVALIDADO EM 09/09/2026 — runtime 2.0.2, data-map loot categories, affix blacklist, current provider presence/absence and Continued-port ID caveats confirmed in global QC #32.
- **Histórico da decisão:**
- **Data da última decisão:** 2026-09-07

> 🔎 **Escopo canônico.** Runtime físico: `apothic_compat-2.0.2.jar`. Esta build NeoForge 1.21.1 é um **data-map compat layer para loot categories do Apotheosis 8.x**. Ela não cria affixes/gems/gear sets; corrige como armas de outros mods são classificadas para que o Apotheosis aplique os affixes/sockets apropriados.

## 1. Por que loot category importa
Apotheosis usa **loot categories** para decidir que affixes e gem socket bonuses são válidos para um item. Se uma arma modded não é categorizada ou cai na categoria errada, pode:
- não receber affixes esperados;
- receber affixes inadequados;
- ter gem bonuses incorretos;
- aparecer de forma errada em equipment comparison.

Apotheosis 8.x já auto-categoriza a maior parte dos itens. Apothic Category Compat existe para preencher **os gaps restantes**.

## 2. Arquitetura na linha NeoForge 1.21.1
Na build 2.0.2, as correções de categoria são fornecidas por **Apotheosis data map**. Isso é diferente das linhas Forge antigas que usavam IMC e heurísticas mais amplas.

A própria documentação deixa claro que features antigas baseadas em mecanismos removidos pelo Apotheosis 8 não estão presentes nesta build.

## 3. Overrides exatos da versão 2.0.2
A release 2.0.2 publica explicitamente os seguintes itens como **`bow`**:

### Alex's Caves
- **Raygun** → bow
- **Dreadbow** → bow

### Alex's Mobs
- **Hemolymph Blaster** → bow
- **Blood Sprayer** → bow

### Born in Chaos
- **Pumpkin Pistol** → bow

### L_Ender's Cataclysm
- **Cursed Bow** → bow
- **Wrath of the Desert** → bow
- **Void Assault Shoulder Weapon** → bow
- **Wither Assault Shoulder Weapon** → bow
- **Laser Gatling** → bow

### The Undergarden
- **Slingshot** → bow

### Twilight Forest
- **Block and Chain** → bow
- **Cube of Annihilation** → bow

A justificativa da linha 2.0.2 para os itens Twilight Forest é que ambos causam dano por **projectile entity**; com categoria bow, bow affixes procs são coerentes e melee affixes não são aplicados indevidamente.

## 4. Quais overrides são relevantes nesta modlist
No snapshot físico atual foram encontrados:
- **Alex's Caves Continued**;
- **Alex's Mobs Continued**;
- **Born in Chaos**;
- **L_Ender's Cataclysm**.

Não foram localizados top-level no snapshot atual:
- **The Undergarden**;
- **Twilight Forest**.

Portanto os overrides desses dois últimos são **dormentes** neste pack enquanto os mods não estiverem presentes.

## 5. Ports Continued — cuidado com IDs
Alex's Caves Continued e Alex's Mobs Continued preservam a identidade dos mods originais em grande parte, mas uma auditoria técnica não deve concluir automaticamente que cada item conserva exatamente o mesmo registry ID só porque o nome visual é o mesmo.

Para confirmar que o override realmente dispara nesta instância:
1. localizar o item no registry/JAR;
2. confirmar namespace + path;
3. inspecionar a loot category resultante no runtime;
4. gerar affix item e verificar pool/procs.

Até esse teste, registrar como **override destinado ao item e potencialmente ativo**, não como prova de aplicação runtime.

## 6. Affix blacklist
A feature de **affix blacklist** continua presente.

Ela impede que affixes listados rolem em **novos gear items**. Importante:
- o blacklist afeta rolls futuros;
- itens que já possuem o affix não perdem o affix automaticamente;
- IDs de affix precisam seguir o path/namespace real da linha 1.21.1;
- reload do blacklist pode ser aplicado pelo comando do mod.

A documentação atual do projeto indica `/apothiccategorycompat reload` ou `/acc reload`; na linha 1.21.1 o comando recarrega a blacklist, enquanto o **data map de categories segue o datapack `/reload` normal**.

## 7. O que NÃO existe nesta build 2.0.2
A release é explícita que mecanismos antigos de 1.20.1 que dependiam de APIs removidas pelo Apotheosis 8 não fazem parte desta build. Não documentar como disponíveis:
- universal attack speed/damage categorization rule;
- split automático sword vs heavy weapon baseado na regra antiga;
- TOML category overrides da linha antiga.

Essas funções aparecem em documentação histórica ou em versões posteriores, mas não pertencem ao contrato 2.0.2 NeoForge 1.21.1.

## 8. Upstream 2.1.0 não é runtime atual
O projeto já possui release **2.1.0**, com recursos/overrides/configuração adicionais. Isso é **drift upstream**, não atualização instalada.

Regra: não importar comportamento de 2.1.0 para a ficha 2.0.2 até o JAR físico mudar e a modlist ser reconciliada.

## 9. Dependência
Na release NeoForge 1.21.1 2.0.2, **Apotheosis é obrigatório**, diferentemente de algumas linhas antigas em que podia ser opcional.

A documentação atual do projeto para 1.21.1 especifica **Apotheosis 8.5 ou superior**. O pack instala **Apotheosis 8.8.0**, atendendo o requisito.

## 10. Relação com Apothic Compats
### Apothic Category Compat
Foco: **corrigir loot category** de itens específicos para que pools de affix/socket sejam apropriados.

### Apothic Compats
Foco mais amplo: datapacks/integrações capazes de acrescentar:
- affixed loot entries;
- gear sets;
- affixes;
- gems;
- invaders;
- categorias/integrations adicionais.

Os dois podem coexistir. Um não é justificativa para remover o outro sem examinar exatamente qual dado cada um fornece.

## 11. Relação com Fallen Gems & Affixes e categorias custom
Versões/documentação mais novas do projeto mencionam categorias adicionais quando outros compats estão presentes. Na build 2.0.2 instalada, não atribuir automaticamente essas integrações sem confirmar o arquivo/data map real.

Se Fallen Gems & Affixes ou outro provider adicionar categoria custom, testar prioridade/ownership da category e o resultado final no Apotheosis.

## 12. Sobreposição funcional
Este mod sobrepõe qualquer datapack/script que altere **a mesma loot category do mesmo item**. O conflito real não é “dois mods de compat”, e sim dois providers escrevendo valores diferentes para a mesma classificação.

Auditoria correta:
- item alvo;
- category final;
- source/provider que escreveu;
- prioridade do data map/datapack;
- affixes/gems resultantes.

## 13. Riscos específicos do pack
1. **Port item IDs diferentes:** override pode não casar com Continued.
2. **Dupla classificação:** outro datapack pode substituir o data map.
3. **Category errada:** ranged weapon receber affix melee ou vice-versa.
4. **Existing gear:** blacklist não remove affix já existente.
5. **Datapack reload:** category e blacklist têm lifecycle diferente.
6. **Version drift:** docs 2.1.0 podem induzir a esperar configs inexistentes em 2.0.2.
7. **Apothic Compats:** integração mais ampla pode mudar loot sem ser problema deste mod.

## 14. Matriz de validação — itens presentes no pack
### Alex's Caves Continued
1. Raygun: category final = bow.
2. Dreadbow: category final = bow.
3. Gerar affixes e confirmar bow procs.

### Alex's Mobs Continued
1. Hemolymph Blaster: category final = bow.
2. Blood Sprayer: category final = bow.
3. Verificar registry IDs usados pelo port.

### Born in Chaos
1. Pumpkin Pistol: category final = bow.
2. Confirmar projectile/ranged affixes e ausência de melee pool indevido.

### Cataclysm
1. Cursed Bow.
2. Wrath of the Desert.
3. Void Assault Shoulder Weapon.
4. Wither Assault Shoulder Weapon.
5. Laser Gatling.
6. Para cada um: category bow + affix proc correto.

## 15. Matriz de validação — sistema
1. Alterar affix blacklist e executar `/acc reload`; gerar gear novo.
2. Confirmar que gear existente mantém affix blacklisted.
3. Executar datapack `/reload`; confirmar categories mantidas/aplicadas.
4. Equipment Comparison reconhecendo item na categoria correta.
5. Gem socket bonus coerente com ranged category.
6. Verificar prioridade frente a qualquer datapack de category custom.
7. Dedicated server: categories server-side sincronizadas ao client via Apotheosis.
8. Remover temporariamente compat em ambiente de teste e comparar category para provar que o override é necessário.

## 16. Regras para outros chats
- Não chamar este mod de “addon de novos affixes”.
- Não usar features históricas de 1.20.1 como se existissem em 2.0.2.
- Não usar docs de 2.1.0 para afirmar comportamento atual.
- Para ports Continued, confirmar IDs reais antes de escrever perk/recipe/compat.
- Ao diagnosticar affix incorreto, verificar loot category final antes de mexer em affix JSON.

## 17. Fontes e confiança
**Authority física:** modlist 07/09/2026 — `apothic_compat-2.0.2.jar`.

**Upstream principal:** [CurseForge — Apothic Category Compat](https://www.curseforge.com/minecraft/mc-mods/apothic-category-compat) e arquivo 2.0.2 NeoForge 1.21.1.

**Confiança:** alta para a lista de overrides, dependência, data-map architecture e limitações 2.0.2, pois estão declaradas diretamente na release. Aplicação aos ports Continued permanece dependente de verificação de registry IDs/runtime.
