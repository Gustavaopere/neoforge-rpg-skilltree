# Create: Ultimate Factory

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db8191b98bc72ae1a10cf6
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist 08.09.2026.txt` / `modlist.txt` — JAR confirmado fisicamente
- **Data da exportação:** 2026-09-10

## Propriedades do banco

- **Mod:** Create: Ultimate Factory
- **Arquivo JAR:** `create_ultimate_factory-2.2.4-neoforge-1.21.1.jar`
- **Versão 1.21.1:** 2.2.4
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Automação, Tecnologia
- **Função:** Recipe pack para Create com cerca de 30 rotas adicionais de automação/renovabilidade, cobrindo materiais vanilla comuns e recursos raros por compacting, mixing, haunting, splashing, crushing e outros processamentos Create.
- **Dependências:** Create obrigatório. JEI é recomendado para visualização. A integração especial com Create Aeronautics na 2.2.4 é condicional à presença do addon e altera especificamente a rota renovável de End Stone.
- **Sobreposição:** Objetivo fortemente próximo de Create: More Automation e outros recipe packs. Redundância deve ser determinada recipe-by-recipe por input, processing type, chance/custo e output, não pelo nome geral de automação.
- **Compatibilidade/Riscos:** Altera fortemente economia/progressão ao tornar vários recursos renováveis, inclusive materiais raros. Sobrepõe recipes com Create: More Automation e outros recipe packs. 2.2.4 adiciona compat Aeronautics para End Stone, remove Blaze Rods do crushing de Scoria, aumenta em 5% o sucesso do compacting de coal blocks e ajusta rates de End Stone.
- **Observações:** mod id conforme runtime do JAR; `create_ultimate_factory-2.2.4-neoforge-1.21.1.jar`, runtime 2.2.4. Projeto anuncia aproximadamente 30 recipes `reasonably balanced` para tornar recursos renováveis. A ficha registra categorias/exemplos oficiais sem inventar chances/quantidades não publicadas.
- **Procedência:** Modlist física canônica de 08/09/2026 + runtime 2.2.4 + CurseForge/Modrinth oficiais da release 2.2.4 e lista pública de recipes do projeto.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/create-ultimate-factory
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 08/09/2026 — 30-recipe renewable-resource authority, Create processing boundaries, rare-resource economy, Aeronautics conditional End Stone path, reload lifecycle e deltas 2.2.4 catalogados.
- **Histórico da decisão:** Sem decisão formal. Em 08/09/2026, Create: Ultimate Factory 2.2.4 foi reconfirmado como `Instalado` e reconstruído ao padrão técnico. A antiga data 27/08 associada a `Sem decisão` foi removida; presença e recomendação histórica não foram tratadas como decisão curatorial.
- **Data da última decisão:**

# Dossiê operacional — padrão Alex's Mobs

> 🏭 Versão física confirmada: `create_ultimate_factory-2.2.4-neoforge-1.21.1.jar`, runtime `2.2.4`, NeoForge 1.21.1. É um **recipe pack de recursos renováveis**, não um novo framework de máquinas.

## 1. Papel e authority
Create: Ultimate Factory registra cerca de **30 recipes** para ampliar automação/renovabilidade. Create continua authority dos processing types e máquinas; Ultimate Factory decide apenas os recipes adicionais e seus inputs/outputs/chances.

## 2. Compacting
A lista pública inclui recipes de compacting para transformar combinações de stone/gravel/fluids e outros materiais em recursos como Tuff, Deepslate, Calcite e materiais de maior valor. O datapack da build instalada é authority de contagens, heat conditions e chances.

## 3. Recursos raros
O objetivo do projeto inclui caminhos renováveis para recursos normalmente limitados ou caros. Isso é uma superfície de progressão crítica: não duplicar os mesmos outputs por KubeJS/outro addon sem comparar rendimento e gates.

## 4. Mixing
Há rotas de Mixing para materiais como Redstone, Dripstone e Gunpowder a partir de inputs alternativos publicados pelo projeto. Create controla o processo do mixer; Ultimate Factory controla a receita.

## 5. Haunting
Recipes públicos usam Haunting para conversões como Basalt→Netherrack, Obsidian→Crying Obsidian, Charcoal→Coal e outras transformações. Uma conversão deve ser liquidada uma única vez pelo recipe manager.

## 6. Splashing
A lista também inclui Splashing, por exemplo Packed Ice→Blue Ice e outras conversões de materiais. Water processing pertence ao Create; o recipe pack não deve executar output por listener paralelo.

## 7. Crushing
Crushing inclui rotas para recursos raros, com exemplos públicos envolvendo Netherite Scrap, Nautilus Shell, Heart of the Sea, Ender Pearl e outros. Chances e quantidades exatas precisam vir do recipe JSON/JAR 2.2.4 quando forem usadas para balanceamento.

## 8. Diamond / coal progression
A documentação pública inclui rota de compacting baseada em coal blocks e lava para Diamond sob condição de heat. A 2.2.4 registra **+5% de sucesso** nessa rota. Não congelar contagem de coal blocks ou chance final nesta ficha sem recipe data exato da build.

## 9. Scoria — 2.2.4
A 2.2.4 **remove Blaze Rods do crushing de Scoria**, nerfando a rota. Regression gate: Scoria não deve continuar produzindo Blaze Rod por recipe stale de versão anterior.

## 10. End Stone e Aeronautics — 2.2.4
A release adiciona compatibilidade com **Create Aeronautics** para End Stone crushing e uma rota renovável especial usando End Stone Powder quando Aeronautics está carregado. Essa integração é condicional; sem Aeronautics, não deve haver classload/recipe fantasma.

## 11. Ajustes de End Stone
O changelog 2.2.4 também registra ajustes menores nas rates de End Stone. Como a publicação consultada não expõe todos os números finais, a ficha não inventa percentuais; recipe JSON da build é authority.

## 12. Recipe overlap com More Automation
Create: More Automation também adiciona rotas de automação. A comparação correta é por **recipe concreto**: input, processing type, heat, chance, throughput e output. Dois mods coexistirem não significa conflito binário, mas pode quebrar progression economy.

## 13. JEI
JEI é recomendado pelo projeto para visualizar as rotas. **JEI não é recipe authority**; Recipe Manager/server prevalece em qualquer divergência ou após datapack reload.

## 14. Datapack e lifecycle
Recipes são data-driven. Validar startup, datapack reload, server restart e mudança de optional integration. Caches/viewers devem refletir imediatamente remoções e alterações sem manter recipes antigos.

## 15. Client/server
Recipe acceptance, material consumption, chances e output são server/common. JEI e tooltips são client-facing. Multiplayer deve resolver a mesma operação uma única vez no servidor.

## 16. Riscos
1. Recipe alternativo contornar progression gate.
2. Mesmo output ter duas rotas excessivamente baratas.
3. Chance/output ser aplicado duas vezes.
4. Recipe antigo de Scoria ainda produzir Blaze Rod.
5. Aeronautics recipe ativar sem provider.
6. End Stone rates ficarem stale após update/reload.
7. JEI mostrar recipe diferente do servidor.
8. Rare-resource automation desbalancear economia do pack.

## 17. Matriz de testes
1. Dedicated server boot.
2. Uma amostra de Compacting, Mixing, Haunting, Splashing e Crushing.
3. Diamond/coal route e comportamento de chance da 2.2.4.
4. Scoria sem Blaze Rod — regression 2.2.4.
5. End Stone com Aeronautics presente.
6. End Stone sem Aeronautics em ambiente isolado, sem classload error.
7. Recursos raros: comparar outputs com outras rotas instaladas.
8. Datapack/recipe reload removendo e restaurando recipe.
9. JEI versus Recipe Manager.
10. Multiplayer/factory contínua verificando conservation de inputs.

## 18. Evidência
- modlist física 08/09/2026: Ultimate Factory 2.2.4;
- projeto oficial: aproximadamente 30 recipes para recursos renováveis, usando processing do Create;
- lista pública de recipes: categorias Compacting, Mixing, Haunting, Splashing, Crushing e outras;
- changelog 2.2.4: Aeronautics/End Stone compat, Scoria sem Blaze Rod, +5% no coal-block compacting e ajustes de End Stone rates.

> 🔒 Boundary canônico: **Ultimate Factory decide receitas adicionais; Create decide a execução do processamento**. A decisão de manter/remover deve ser econômica, recipe-by-recipe, não baseada apenas em sobreposição temática.