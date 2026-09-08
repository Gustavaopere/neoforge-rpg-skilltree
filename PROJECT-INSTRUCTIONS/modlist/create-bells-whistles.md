# Create: Bells & Whistles

> Fonte canônica desta importação: Notion — `Auditoria Mestre da Modlist — NeoForge 1.21.1`  
> Página-fonte: https://app.notion.com/p/3c369db9f0db81b583c7cbd2681a24f7  
> Estado no momento da exportação: `Instalado — Dossiê completo`  
> Autoridade física no momento da exportação: `modlist(4).txt`, 595 mods  
> Exportado em: 2026-09-08

## Propriedades do registro

- **Mod:** Create: Bells & Whistles
- **Arquivo JAR:** `bellsandwhistles-0.4.7-1.21.1.jar`
- **Versão 1.21.1:** `0.4.7-1.21.1`
- **Categoria:** Tecnologia; Visual
- **Decisão:** Sem decisão
- **Estado da pesquisa:** Verificado
- **Estado no pack na origem:** Instalado — Dossiê completo
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/create-bells-whistles
- **Função:** Addon Create para construção ferroviária com Train Pilots, Grab Bars, Bogie Steps e Station Platform Block, incluindo peças ladder-aware que funcionam em trains.
- **Dependências:** Create; pack usa Create 6.0.10.
- **Compatibilidade/Riscos:** Version drift com Create 6.0.10, collision/ladder em contraptions, assemble/disassemble e sobreposição visual com outros addons ferroviários. Create mantém authority de trains/contraptions.
- **Sobreposição:** Sobreposição parcial de decoração/ferrovia com outros addons Create, sem equivalência automática.
- **Observações:** README lista 9 Train Pilots, 3 Grab Bars, 3 Bogie Steps e 1 Station Platform Block = 16 entradas/variantes principais; não rotulado como registry count binário exato.
- **Procedência:** Modlist física atual de 07/09/2026 + CurseForge oficial + README/source oficial Create: Bells & Whistles 0.4.7.
- **Histórico da decisão:** Sem decisão formal. Em 08/09/2026, a auditoria confirmou Create: Bells & Whistles 0.4.7-1.21.1, seu escopo ferroviário/decorativo e a authority do Create sobre trains/contraptions. A sobreposição visual com outros addons não foi usada para inferir decisão.
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 07/09/2026 — inventário ferroviário principal, authority Create, contraption/ladder lifecycle e riscos catalogados.
- **Data da última decisão:** não definida.

## Dossiê operacional — padrão Alex's Mobs

> ✅ Versão física confirmada: `bellsandwhistles-0.4.7-1.21.1.jar`, mod id `bellsandwhistles`, runtime `0.4.7-1.21.1`. O pack usa Create `6.0.10`. O README oficial lista o conteúdo funcional/decorativo principal abaixo.

## 1. Papel e autoridade
Create: Bells & Whistles é um addon de **construção ferroviária e decoração funcional para Create**, especialmente peças usadas em trens. Create permanece authority de trains, contraptions, assembly, movement e kinetic semantics; Bells & Whistles registra blocos/variantes para ampliar construção e acesso em veículos.

## 2. Train Pilots — 9 variantes documentadas
O README oficial lista nove Train Pilots:
1. Metal
2. Andesite Alloy
3. Brass
4. Copper
5. Polished Crimsite
6. Polished Veridium
7. Polished Asurine
8. Polished Ochrum
9. Polished Limestone

Essas variantes são conteúdo visual/funcional do addon. Não tratá-las como nove sistemas de controle diferentes sem evidência de comportamento distinto.

## 3. Grab Bars — 3
- Andesite Grab Bar
- Brass Grab Bar
- Copper Grab Bar

O projeto documenta comportamento de **ladder** e funcionamento em trains. Isso exige preservar collision/climb semantics tanto em mundo quanto quando o bloco integra uma contraption montada.

## 4. Bogie Steps — 3
- Andesite Bogie Step
- Brass Bogie Step
- Copper Bogie Step

Os Bogie Steps estendem uma peça um bloco abaixo, possuem comportamento de ladder e são feitos para funcionar em trens. A posição/shape visual deve acompanhar transformação da contraption sem criar bloco fantasma no mundo.

## 5. Station Platform Block — 1
O README também lista um **Station Platform Block**, voltado à composição estética/estrutural de estações. Não foi atribuído mecanismo de estação extra além do que a documentação confirma.

## 6. Inventário funcional documentado
O conjunto oficial acima totaliza **16 entradas/variantes listadas no README**: 9 Train Pilots + 3 Grab Bars + 3 Bogie Steps + 1 Station Platform Block. A ficha usa “listadas” e não “registry count exato” porque o source registry completo da build não foi usado para excluir eventuais itens auxiliares.

## 7. Crafting e stonecutting
Grab Bars e Bogie Steps são descritos como obtíveis via crafting/stonecutter. Recipes pertencem ao addon/datapack e devem ser alteradas por datapack/KubeJS somente quando necessário, sem duplicar receitas equivalentes automaticamente.

## 8. Integração com Create 6.0.10
Pontos de authority:
- montagem/movimento do train = Create;
- transformação de blocos na contraption = Create;
- ladder/collision/shape da peça = bloco do addon + engine Create quando montado;
- visual/material variants = Bells & Whistles.

Não reimplementar movement de peça ferroviária em mod próprio.

## 9. Client/server e lifecycle
- placement, collision e estado da contraption são servidor/common;
- modelos/texturas são cliente;
- assemble/disassemble precisa conservar bloco/state exatamente uma vez;
- chunk unload/reload de train não pode gerar cópia do step/grab bar;
- resource reload só altera assets, não composition do train.

## 10. Riscos
1. Version drift com Create 6.0.10.
2. Ladder/climb divergir entre bloco estático e contraption.
3. Shape/collision fantasma após assemble/disassemble.
4. Render transform incorreto em bogie step estendido.
5. Sobreposição visual com outros addons ferroviários ser confundida com conflito funcional.
6. Recipe duplication via datapack/KubeJS.

## 11. Matriz de testes
1. Dedicated server boot com Create 6.0.10.
2. Colocar cada família de bloco e validar shape/collision.
3. Grab Bars e Bogie Steps como ladder estática e em train em movimento.
4. Assemble/disassemble/reassemble sem dupe ou bloco órfão.
5. Save/reload e chunk unload de train.
6. Resource reload e diferentes materiais/modelos.
7. Crafting/stonecutter sem recipe collision relevante.

## 12. Evidência
- modlist física atual: Bells & Whistles 0.4.7-1.21.1 e Create 6.0.10;
- CurseForge oficial 0.4.7;
- README/source oficial com Train Pilots, Grab Bars, Bogie Steps e Station Platform Block.

> 🚂 Conteúdo documentado: 16 entradas/variantes principais no README. Create continua authority de trains/contraptions; Bells & Whistles fornece as peças e seus comportamentos específicos.
