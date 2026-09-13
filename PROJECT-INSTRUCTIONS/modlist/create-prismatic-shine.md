# Create: Prismatic Shine

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db816785f1c0835731a49e
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist.txt` — 595 mods top-level
- **Data da exportação:** 2026-09-10

## Propriedades do banco

- **Mod:** Create: Prismatic Shine
- **Arquivo JAR:** `createprism-1.2.2.jar`
- **Versão 1.21.1:** 1.2.2
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Visual, Tecnologia
- **Função:** Addon decorativo/funcional para Create que reimplementa a ideia do antigo Create: Crystal Clear com glass casings, glass scaffolding e illumination casings que emitem luz, além de recipes alinhados ao fluxo vanilla/Create.
- **Dependências:** Create. Release 1.2.2 é NeoForge 1.21.1, Client & Server. Nenhuma dependência obrigatória adicional foi inferida da página pública da build.
- **Sobreposição:** Sobreposição estética com Create Deco, Design n' Decor e outros casing/decor addons. Prismatic Shine possui sua própria linha de glass/illumination casings; redundância deve ser avaliada por bloco/recipe/uso concreto.
- **Compatibilidade/Riscos:** Principal superfície é block/model/render/light behavior e recipes de casing. 1.2.2 tem changelog público curto `fix server issue`; o detalhe causal não é publicado, portanto dedicated-server boot é regression gate obrigatório. Sobreposição estética com outros casing/decor addons não implica redundância binária.
- **Observações:** mod id `createprism`; runtime 1.2.2. Projeto remake de Create: Crystal Clear para Create 6.0/NeoForge 1.21.1. Adiciona glass casings, glass scaffold e illumination casings. Fluxo documentado inclui aplicar Andesite Alloy em glass, processar clear casing via Stonecutter/Mechanical Saw e aplicar Prismarine Crystals para illumination casing.
- **Procedência:** Modlist física canônica de 08/09/2026 + runtime `createprism` 1.2.2 + Modrinth/CurseForge oficiais da release 1.2.2 e descrição oficial do projeto.
- **Fonte:** https://modrinth.com/mod/create-prismatic-shine/version/1.2.2
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 08/09/2026 — glass/illumination-casing authority, recipes/processing, real-light versus emissive-render boundary, client/server lifecycle e regression gate `fix server issue` da 1.2.2 catalogados.
- **Histórico da decisão:** Sem decisão formal. Em 08/09/2026, Create: Prismatic Shine 1.2.2 foi reconfirmado como `Instalado` e reconstruído ao padrão técnico. A natureza decorativa não foi convertida automaticamente em decisão de manter/remover.
- **Data da última decisão:**

# Dossiê operacional — padrão Alex's Mobs

> 💎 Versão física confirmada: `createprism-1.2.2.jar`, mod id `createprism`, runtime `1.2.2`, NeoForge 1.21.1. O projeto é um **remake/continuação conceitual de Create: Crystal Clear** para Create 6.0, com glass e illumination casings.

## 1. Papel e authority
Create: Prismatic Shine adiciona blocks decorativos/funcionais integrados ao visual do Create. **Create** continua authority de shafts/cogwheels/kinetic blocks; Prismatic Shine controla suas famílias de glass casing, scaffold, illumination casing e recipes próprios.

## 2. Glass casings
O projeto adiciona **glass casings** que permitem aparência transparente/industrial alinhada ao Create. O casing é block state real; resource packs podem mudar texture/model, mas não devem alterar recipe ou propriedades server-side.

## 3. Clear glass casings
A documentação oficial mostra um fluxo no qual glass recebe Andesite Alloy para formar Andesite Glass Casing, que pode ser convertido em **Andesite Clear Glass Casing** por Stonecutter ou Mechanical Saw.
Recipe Manager é authority. Se KubeJS alterar a cadeia, substituir o recipe de modo explícito em vez de manter duas rotas acidentais.

## 4. Illumination casings
Aplicar **Prismarine Crystals** a Andesite Clear Glass Casing produz Andesite Illumination Casing conforme exemplo oficial. A proposta do bloco é emitir luz além de manter o estilo glass casing.
Light emission real deve ser distinguida de emissive texture/shader: iluminação de mundo é gameplay/block property; glow visual é presentation.

## 5. Glass scaffold
O addon também adiciona **glass scaffolding**. Collision/climb/support behavior exato deve ser verificado no runtime antes de qualquer integração própria; esta ficha não o equipara automaticamente ao scaffolding vanilla ou a blocks de outros addons.

## 6. Relação com Crystal Clear
Prismatic Shine existe porque Create: Crystal Clear ficou sem atualização para o alvo moderno, segundo o autor. Ele reimplementa a ideia e adiciona illumination casings.
Isso é origem conceitual, não garantia de compatibilidade binária ou paridade 1:1 de IDs/recipes com Crystal Clear antigo.

## 7. Create 6.0
O projeto é direcionado a NeoForge 1.21.1/Create 6.0. Blocks encased devem acompanhar mudanças de models/kinetic rendering do Create sem duplicar a máquina base.
Um encased shaft/cogwheel continua tendo kinetics decidida pelo Create; o addon controla casing/model/state adicional.

## 8. Recipes e processing
Stonecutter, Mechanical Saw e right-click application aparecem no fluxo público. Em automação, input precisa ser consumido exatamente uma vez e output/state transformado uma vez.
Deployers ou scripts externos não devem reaplicar a mesma transformação já reconhecida pelo block interaction do addon.

## 9. Tags e materials
Versões anteriores da linha 1.21.1 tiveram fixes de tags. Isso torna interoperabilidade de materials/recipes uma superfície relevante, mas a ficha não inventa tags concretas não pinadas à 1.2.2.
Para unificação com outros casing addons, inspecionar tags/data reais antes de alterar recipes.

## 10. Lighting
Illumination casing deve ser validado com light engine real: colocar/remover, chunk reload e neighbor update. Shaders, Sodium-like renderers ou emissive packs podem alterar aparência, mas não o light level lógico do bloco.

## 11. Client/server — regression 1.2.2
A release **1.2.2** possui changelog público apenas como **`fix server issue`**. Como o upstream não explica a causa, a ficha não inventa classe/bug específico.
Dedicated server boot e placement/use de todas as famílias de casing são regression gates obrigatórios para a build instalada.

## 12. Resource lifecycle
Models/textures de blocks transparentes são sensíveis a resource reload e render pipelines. Resource reload não deve alterar block state nem perder casing transformation server-side.

## 13. Contraptions
Casings em componentes Create podem aparecer em contraptions. Assembly/disassembly deve preservar block state visual sem criar shaft/cogwheel duplicado ou perder kinetic identity.

## 14. Coexistência decorativa
Create Deco, Design n' Decor e outros addons podem oferecer glass, lamps ou casings. Sobreposição estética não é substituição: comparar block palette, recipes, light emission e uso em machines antes de decidir redundância.

## 15. Riscos
1. Dedicated server regression — 1.2.2 foi publicada para `fix server issue`.
2. Transparent model/render incorreto.
3. Illumination visual não corresponder ao light engine real.
4. Casing conversion consumir item duas vezes.
5. Tags/recipes colidirem com outro casing addon.
6. Contraption perder casing state.
7. Crystal Clear antigo ser tratado como compatível por ID sem prova.

## 16. Matriz de testes
1. Dedicated server boot — regression 1.2.2.
2. Client join e creative/recipe discovery.
3. Glass → Andesite Glass Casing.
4. Stonecutter/Mechanical Saw → Clear Glass Casing.
5. Prismarine Crystals → Illumination Casing.
6. Colocar/remover illumination casing e medir light update.
7. Resource reload com blocks colocados.
8. Encased kinetic component em operação.
9. Assembly/disassembly em contraption simples.
10. Coexistência visual/recipe com outros decor/casing addons instalados.

## 17. Evidência
- modlist física 08/09/2026: Prismatic Shine 1.2.2;
- Modrinth/CurseForge oficiais: remake de Crystal Clear, glass casings, glass scaffold e illumination casings, NeoForge 1.21.1 Client & Server;
- documentação oficial: fluxo Andesite Alloy → clear casing via Stonecutter/Saw → Prismarine Crystals → illumination casing;
- changelog 1.2.2: `fix server issue`, sem detalhe causal público.

> 🔒 Boundary canônico: **Prismatic Shine controla casing/iluminação; Create controla o componente cinético encased**. Render transparente e luz visual não substituem state server-side.
