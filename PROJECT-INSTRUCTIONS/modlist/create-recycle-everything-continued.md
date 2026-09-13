# Create: Recycle Everything Continued

> Fonte canônica desta importação: Notion — `Auditoria Mestre da Modlist — NeoForge 1.21.1`  
> Página-fonte: https://app.notion.com/p/3c369db9f0db81ea9dc6eb32e320a248  
> Estado no momento da exportação: `Instalado — Dossiê completo`  
> Autoridade física no momento da exportação: modlist física mais recente, 595 mods  
> Exportado em: 2026-09-08

## Propriedades do registro

- **Mod:** Create: Recycle Everything Continued
- **Arquivo JAR:** `create_recycle_everything-2.1.0.jar`
- **Versão 1.21.1:** `1.1`
- **Categoria:** Automação; Tecnologia
- **Decisão:** Sem decisão
- **Estado da pesquisa:** Verificado
- **Estado no pack na origem:** Instalado — Dossiê completo
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/create-recycle-everything-continued
- **Função:** Recipe addon de Create para reciclar itens por Crushing Wheels e recuperar materiais de equipamentos/blocos vanilla, Create e integrações suportadas.
- **Dependências:** Create obrigatório. A build 2.1.0 adiciona integração de recipes para Create Stuff 'N Additions; outras integrações só contam quando o mod alvo está presente.
- **Compatibilidade/Riscos:** Aumenta recuperação de materiais e pode contornar progression/custos quando coexistem outras rotas de reciclagem. 2.1.0 adiciona crushing de diamond/netherite armor, hopper, horse armors e crushing wheel, além de jetpacks/exoskeletons de Create Stuff 'N Additions. Evitar double-recycling por recipes duplicados.
- **Sobreposição:** Sobrepõe material recovery/recipe space de outros recycle packs, mas não Almost Unified: este mod cria rotas de reciclagem; unifiers reconciliam equivalências/outputs. Avaliar recipe-by-recipe.
- **Observações:** JAR `create_recycle_everything-2.1.0.jar`; publicação/filename 2.1.0, metadata runtime literal 1.1. A divergência é preservada fail-closed. Changelog 2.1.0: reorganização interna e novos crushing recipes, incluindo integração Create Stuff 'N Additions.
- **Procedência:** Modlist física canônica de 08/09/2026 + metadata runtime 1.1 + CurseForge oficial da publicação 2.1.0 e changelog exato.
- **Histórico da decisão:** Sem decisão formal. Em 08/09/2026, Create: Recycle Everything Continued foi reconfirmado como `Instalado`; a divergência 2.1.0↔1.1 foi preservada e presença não foi convertida em decisão curatorial.
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 08/09/2026 — crushing/recycling recipe authority, material-recovery economy, conditional integrations, recipe reload lifecycle e divergência filename 2.1.0 ↔ runtime 1.1 catalogados.
- **Data da última decisão:** não definida.

## Dossiê operacional — padrão Alex's Mobs

> ♻️ Identidade física: `create_recycle_everything-2.1.0.jar`. A publicação/filename é **2.1.0**, enquanto a metadata runtime da instância declara literalmente **`1.1`**. As duas evidências são preservadas separadamente.

## 1. Papel e authority
Create: Recycle Everything Continued é um **recipe addon**. Ele registra rotas de recuperação de materiais, sobretudo por Crushing Wheels. Create continua authority do processing mecânico; este addon decide quais inputs podem ser reciclados e quais outputs/chances seus recipes geram.

## 2. Crushing como rota de reciclagem
O princípio operacional é transformar itens acabados de volta em parte de seus materiais. Isso altera economia/progressão, mas não cria uma nova máquina independente: a execução usa processing do Create.

## 3. Delta 2.1.0 — armaduras
O changelog exato adiciona crushing recipes para **Diamond Armors** e **Netherite Armors**. Recuperação deve seguir o recipe real da build; durability, enchantments ou NBT não devem ser convertidos por scripts externos em valor adicional sem regra explícita.

## 4. Delta 2.1.0 — outros itens
A mesma release adiciona recipes para **Hopper, Horse Armors e Crushing Wheel**. Esses itens podem ter outras rotas de desmontagem em mods diferentes; coexistência precisa ser comparada por rendimento, não apenas por output final.

## 5. Create Stuff 'N Additions
2.1.0 adiciona integração com **Create Stuff 'N Additions**, incluindo Jetpacks e Exoskeletons. A integração é condicional ao provider estar realmente presente. Não registrar esse mod como conteúdo próprio do recycler.

## 6. Recipe identity e datapacks
Recipe Manager/datapacks são a authority do conjunto ativo. KubeJS ou outro pack de dados pode substituir/remover recipes, mas deve usar IDs reais e evitar deixar duas rotas semanticamente iguais por acidente.

## 7. Conservation de materiais
Uma operação aceita deve consumir o input exatamente uma vez e produzir apenas o output definido. Retry, chunk unload ou viewer refresh não podem reexecutar o mesmo crushing settlement.

## 8. Itens com estado
Equipamentos podem carregar damage, enchantments, custom name ou components. A ficha não presume como cada recipe trata esses estados sem leitura do datapack/JAR exato; esse comportamento deve ser testado antes de permitir reciclagem de itens especiais em automação massiva.

## 9. Sobreposição econômica
O risco principal é **material recovery excessivo** quando outras receitas também desmontam o mesmo item. Se duas rotas forem válidas, comparar custo energético/tempo, recovery rate e progression gate antes de decidir remoção ou override.

## 10. Relação com unificação
Almost Unified e sistemas semelhantes lidam com equivalência de materiais/outputs. Recycle Everything cria recipes de recuperação. Uma ferramenta de unificação não deve ser tratada como substituto automático deste addon.

## 11. Client/server
Recipe resolution, input consumption e output são common/server-authoritative. JEI/recipe viewers são client-facing e não determinam se um recipe realmente existe no servidor.

## 12. Lifecycle
Validar startup, datapack/recipe reload, server restart, Crushing Wheel processing, removal/addition de integração condicional e atualização do Create.

## 13. Divergência de versão
Registrar sempre separadamente:
- filename/publicação: `2.1.0`;
- runtime metadata: `1.1`.

O campo `Versão 1.21.1` preserva o runtime observado. Não corrigir automaticamente uma string pela outra.

## 14. Riscos
1. Duas rotas reciclarem o mesmo item com rendimento incompatível.
2. Item ser consumido duas vezes ou output duplicado.
3. Equipamento com NBT/components perder ou gerar valor indevido.
4. Integração condicional carregar sem provider.
5. Recipe viewer ficar stale após reload.
6. Divergência 2.1.0↔1.1 atrapalhar troubleshooting se for normalizada.

## 15. Matriz de testes
1. Dedicated server boot.
2. Diamond armor e Netherite armor de cada família representativa.
3. Hopper, horse armor e Crushing Wheel.
4. Create Stuff 'N Additions apenas se instalado: Jetpack/Exoskeleton.
5. Itens danificados/enchantados em cópia de teste.
6. Conservation de input/output em automação contínua.
7. Recipe reload removendo um recipe e reativando-o.
8. Comparação com outras rotas de reciclagem instaladas.
9. JEI/Recipe Manager após reload.
10. Restart sem recipe duplication.

## 16. Evidência
- modlist física 08/09/2026: `create_recycle_everything-2.1.0.jar`, runtime 1.1;
- CurseForge oficial: release NeoForge 1.21.1 v2.1.0;
- changelog 2.1.0: restructure interno; crushing recipes de diamond/netherite armor, hopper, horse armors e Crushing Wheel; integração Create Stuff 'N Additions para Jetpacks/Exoskeletons.

> 🔒 Boundary canônico: **Recycle Everything define recipes de recuperação; Create executa o crushing**. Toda reciclagem deve conservar um único input→output settlement e a divergência de versão deve permanecer visível.
