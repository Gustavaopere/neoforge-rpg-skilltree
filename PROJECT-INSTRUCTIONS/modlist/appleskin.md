# AppleSkin

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db81889791c3f4d3feabbe
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist.txt` — 595 mods top-level
- **Data da exportação:** 2026-09-09

## Propriedades do banco

- **Mod:** AppleSkin
- **Arquivo JAR:** `appleskin-neoforge-mc1.21-3.0.9.jar`
- **Versão 1.21.1:** 3.0.9+mc1.21
- **Estado no pack:** Integrado ao Github
- **Estado da pesquisa:** Verificado
- **Decisão:** Manter
- **Categoria:** Comida, QoL
- **Função:** Telemetria/HUD de alimentação: mostra hunger, saturation, exhaustion, valores de alimentos, restauração prevista e saúde potencial recuperável em tooltips/HUD/F3 sem alterar a mecânica de comida.
- **Dependências:** Sem dependência funcional de outro mod além da plataforma. É majoritariamente client-side, mas deve estar no servidor quando se deseja sincronização precisa de saturation/exhaustion.
- **Sobreposição:** Não substitui Nutritional Balance, Farmer's Delight, Thirst ou outro sistema de sobrevivência; apenas expõe visualmente hunger/saturation/exhaustion e valores relacionados.
- **Compatibilidade/Riscos:** Riscos são principalmente visuais e de representação: stacking com outros HUDs, estados montados e alimentos com mecânicas customizadas fora do pipeline normal. Não usar tooltip/HUD como authority técnica de progressão. Nutritional Balance, sede e efeitos especiais de comida permanecem sistemas separados.
- **Observações:** mod id: `appleskin`; JAR 3.0.9, metadata runtime 3.0.9+mc1.21. A página documenta client/server sync, features HUD, limites, integração com food mods e testes de overlays.
- **Procedência:** modlist.txt física atual de 11/09/2026 + CurseForge/source/releases oficiais AppleSkin 3.0.9 e fontes já auditadas no dossiê. Reconciliação final: JAR permanece `appleskin-neoforge-mc1.21-3.0.9.jar` e metadata runtime `3.0.9+mc1.21`; sem divergência física.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/appleskin
- **Atualização/Status:** PADRÃO ALEX'S MOBS REVALIDADO EM 11/09/2026 — reconciliação final física #37: `appleskin-neoforge-mc1.21-3.0.9.jar` / metadata runtime `3.0.9+mc1.21` conferidos contra a modlist atual; corpo técnico, decisão e estado preservados.
- **Histórico da decisão:** Manter. Em 07/09/2026 a ficha foi refeita como dossiê operacional de observabilidade, distinguindo AppleSkin de nutrição, sede e culinária e registrando as correções NeoForge da linha 3.0.9.
- **Data da última decisão:** 2026-09-07

> 🍎 **PADRÃO ALEX'S MOBS — DOSSIÊ OPERACIONAL EXAUSTIVO.** Esta ficha documenta `appleskin-neoforge-mc1.21-3.0.9.jar`, mod id `appleskin`, metadata runtime **`3.0.9+mc1.21`**. Como AppleSkin não é um content mod, o padrão exaustivo aqui significa inventariar **todas as superfícies observáveis confirmadas** — tooltips, saturation, exhaustion, previsão de hunger/saturation/health, F3, sync servidor→cliente e API de overlays — sem inventar itens, blocos, entidades ou progressão inexistentes.

## 1. Identidade e versão
- **Mod:** AppleSkin.
- **JAR físico:** `appleskin-neoforge-mc1.21-3.0.9.jar`.
- **Runtime:** `3.0.9+mc1.21`.
- **Loader:** NeoForge.
- **Minecraft:** linha 1.21, usada no pack 1.21.1.
- **Papel:** mostrar informações de alimentação que o vanilla normalmente oculta.
- **Decisão:** **Manter**.

## 2. Funções reais
AppleSkin acrescenta principalmente informação visual:
- valores de fome/saturação em **tooltips de alimentos**;
- visualização de **saturação** no HUD;
- visualização de **exhaustion**;
- previsão de fome e saturação que serão restauradas pelo alimento atualmente segurado;
- previsão de **vida potencialmente recuperável** ao comer, de acordo com o estado de fome/regeneração;
- valores de hunger/saturation/exhaustion no **F3/debug overlay**.

Essas superfícies tornam o sistema de alimentação mensurável sem alterar receitas, valores nutricionais ou lógica de consumo.

## 3. Client-side versus server-side
O projeto é majoritariamente client-side, mas a documentação oficial ressalta que o mod deve estar também no servidor quando se deseja mostrar **valores exatos de saturation/exhaustion** no cliente. O motivo é sincronização de dados que o vanilla não expõe integralmente ao cliente.

No pack, tratar AppleSkin como mod de UX presente em ambos os lados evita diagnósticos falsos do tipo “o HUD está errado” quando, na verdade, faltou sincronização server-side.

## 4. Relação com sistemas de comida do pack
AppleSkin é **read-only para gameplay**. Ele observa valores do sistema de fome e alimentos; não cria uma segunda barra de nutrição.
- **Farmer's Delight e addons:** seus alimentos podem ser exibidos pelo AppleSkin quando usam os componentes/valores normais reconhecidos.
- **Nutritional Balance:** continua sendo provider de nutrição/variedade quando aplicável; AppleSkin não calcula essa progressão.
- **Thirst:** é sistema separado. O HUD de comida não deve ser confundido com hidratação.
- **Efeitos especiais de alimentos:** só aparecem naquilo que o Minecraft/mod expõe ao tooltip; AppleSkin não é authority sobre efeitos customizados de todos os food mods.

## 5. Mudanças relevantes da 3.0.9
Na linha 3.0.9, o upstream corrigiu em NeoForge:
- overlays que não eram ocultados corretamente enquanto o jogador estava montado;
- a opção `showFoodValuesHudOverlay`, que não escondia corretamente a saturação prevista do alimento segurado.

Isso é importante no pack porque há várias montarias/veículos e interfaces de combate; qualquer sobreposição visual deve ser testada antes de atribuir o problema a outro HUD.

## 6. Compatibilidade e riscos
1. **HUD stacking:** outros mods podem desenhar barras/overlays próximos; validar resolução, GUI scale e estados de combate/montaria.
2. **Dados customizados:** alimentos que implementam mecânica fora do pipeline normal podem não ser representados integralmente.
3. **Servidor sem AppleSkin:** pode reduzir precisão de saturation/exhaustion exibidos.
4. **Não usar como authority de perk:** ler a tooltip/HUD não é um hook técnico seguro para conceder progressão; usar dados reais do provider.
5. **Resource packs:** podem alterar textura/posição visual, mas não mudam a semântica dos valores.

## 6.1 Superfícies técnicas e API
O README oficial documenta uma API própria para integração opcional. O contrato é propositalmente de **apresentação/telemetria**, não de autoridade de gameplay:
- mods podem compilar contra a API sem exigir AppleSkin em runtime;
- handlers podem ser registrados apenas quando `appleskin` está carregado;
- o pacote de API expõe eventos de overlay/tooltip para permitir que consumers ocultem, alterem ou complementem a apresentação;
- saturation/exhaustion são sincronizados ao cliente para tornar o HUD preciso quando o servidor também possui AppleSkin.

### Boundary de integração
- **Dado real de fome/saturation/exhaustion:** pertence ao estado do jogador/Minecraft e aos providers que o modificam.
- **AppleSkin:** observa/sincroniza/renderiza esses valores.
- **Perks/quests:** nunca devem detectar consumo ou recompensa lendo o HUD, tooltip ou evento visual; usar o evento/estado server-side do provider real.

### Conteúdo registrado
Nesta auditoria **não foi encontrado conteúdo jogável próprio que justifique catálogo de mobs, itens, blocos, recipes ou worldgen**. Isso é uma diferença de escopo em relação a Alex's Mobs, não falta de profundidade: o inventário exaustivo de AppleSkin é seu pipeline de observabilidade.

## 7. Sobreposição e decisão
AppleSkin não é redundante com mods de nutrição: seu domínio é **observabilidade de hunger/saturation/exhaustion**. Também não altera gameplay como um overhaul de comida.

**Decisão operacional: manter.** O custo mecânico é baixo e a legibilidade que fornece é especialmente útil em um pack com muitas comidas e sistemas de sobrevivência.

## 8. Matriz mínima de teste
- tooltip de alimento vanilla e Farmer's Delight;
- previsão de hunger/saturation antes e depois de comer;
- saturation/exhaustion com servidor dedicado;
- overlay enquanto montado;
- opção `showFoodValuesHudOverlay` ligada/desligada;
- compatibilidade visual com HUDs de Epic Fight, sede, magia e demais overlays;
- F3 com valores habilitados.

## 9. Fontes
- [CurseForge — AppleSkin](https://www.curseforge.com/minecraft/mc-mods/appleskin)
- [Source — AppleSkin](https://github.com/squeek502/AppleSkin)
- [Releases oficiais](https://github.com/squeek502/AppleSkin/releases)
- Modlist física e guia consolidado de Gameplay do projeto.
