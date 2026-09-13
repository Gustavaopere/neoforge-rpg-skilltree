# Create: Metalwork

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db811f9732ef4c4a0e208e
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist.txt` — 595 mods top-level
- **Data da exportação:** 2026-09-10

## Propriedades do banco

- **Mod:** Create: Metalwork
- **Arquivo JAR:** `createmetalwork-2.0.0.jar`
- **Versão 1.21.1:** 2.0.0
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Metalurgia, Automação, Tecnologia
- **Função:** Adiciona novos fluidos/crushed ores e rotas metalúrgicas do Create voltadas a aumentar rendimento de ingots por minério.
- **Dependências:** Create obrigatório. Almost Unified 1.4.2 está presente e é recomendado pelo projeto para reduzir variantes. Big Cannons 5.11.7, Dreams n' Desires 2.3a-BETA e Northstar 0.6.4 também estão presentes como superfícies de compatibilidade a validar.
- **Sobreposição:** Forte overlap econômico/material com Create Metallurgy 1.0.3 e Productive Metalworks 1.15.1. Não há duplicata automática: comparar crushed ores, molten forms, recipe ratios e máquinas; Almost Unified pode unificar variantes sem resolver rendimento.
- **Compatibilidade/Riscos:** Riscos: receitas paralelas com rendimentos incompatíveis; conversion loops; molten fluid amounts semanticamente diferentes; Almost Unified escolher variante inadequada; drift de CBC/DnD/Northstar; KubeJS stale recipes; regressão do crash Lithium se o provider for adicionado.
- **Observações:** JAR/mod id/runtime 2.0.0 confirmados. Release exata NeoForge 1.21.1 corrige crash com Lithium. O repositório público localizado permanece na linha antiga 1.20.1/1.0.0 e não foi usado como implementação binária da 2.0.0.
- **Procedência:** modlist.txt física atual de 08/09/2026 — 595 mods top-level + release/documentação oficiais Create: Metalwork 2.0.0. Source público antigo foi tratado apenas como histórico, não como equivalência.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/create-metalwork
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 09/09/2026 — dossiê 2.0.0 com crushed/molten ore processing, rendimento, Almost Unified, overlaps metalúrgicos, compatibilidade CBC/DnD/Northstar e fix de crash com Lithium catalogados.
- **Histórico da decisão:** Histórico não operacional: uma auditoria anterior centrada em TFC sugeriu remoção por criar rota metálica concorrente. TFC não está na modlist atual, portanto esse fundamento não é aplicável à decisão presente. O mod permanece instalado e sem decisão final.
- **Data da última decisão:** 2026-08-22

# Dossiê operacional — padrão Alex's Mobs

> ⚙️ **Identidade física confirmada:** `createmetalwork-2.0.0.jar`, mod id `createmetalwork`, runtime `2.0.0`, NeoForge 1.21.1. A release exata é Client & Server e registra como delta específico a correção de crash com Lithium. O repositório público disponível está numa linha antiga 1.20.1 e não é tratado como source binário da 2.0.0.

## 1. Papel e authority
Create: Metalwork adiciona novas rotas de **ore processing**, crushed ores e molten materials com foco em aumentar o rendimento de ingots por minério usando o ecossistema Create. O addon owns seus itens/fluidos/recipes; Create continua authority das máquinas e tipos de processamento base reutilizados.

## 2. Objetivo de rendimento
A documentação oficial declara explicitamente que as rotas do mod produzem rendimentos superiores à fundição vanilla. Isso afeta economia/progressão do pack e precisa ser comparado com outros processadores instalados por input, output e energia/tempo, não apenas por nome de material.

## 3. Crushed ores
O addon adiciona **novos crushed ores** para materiais suportados. Registry/tags reais da build 2.0.0 são authority da lista exata; esta ficha não retroprojeta IDs da antiga branch 1.20.1.

## 4. Molten ores/metals
Também adiciona versões **molten** de ores/metais para permitir cadeias fluid-based. Tipo e amount devem permanecer consistentes entre melting, transporte e solidificação; fluidos homônimos de outro mod só são equivalentes quando tags/recipes os unificam explicitamente.

## 5. Rotas Create de processamento
Metalwork foi projetado para ampliar a transformação de minério em ingots dentro da automação Create. Recipe Manager é authority de cada etapa, quantidade e chance. Viewer não deve ser usado como prova de rendimento se datapacks/KubeJS alterarem recipes.

## 6. Compatibilidade declarada com outros mods
A documentação pública histórica lista integrações com vários providers, incluindo Create, Create: Big Cannons, Create: Dreams & Desires e Create: Northstar. No pack atual, Big Cannons 5.11.7, Dreams n' Desires 2.3a-BETA e Northstar 0.6.4 estão presentes; cada material compartilhado precisa ser testado pela recipe/tag real da build 2.0.0.

## 7. Almost Unified
O próprio projeto recomenda **Almost Unified** para reduzir sobreposição de materiais. O pack contém Almost Unified 1.4.2. A unificação pode escolher uma variante canônica de item/fluid, mas não deve alterar silenciosamente rendimento nem criar ida-e-volta lucrativa entre recipes equivalentes.

## 8. Create Metallurgy
Create Metallurgy 1.0.3 está instalado e possui machinery de melting/casting própria. Há overlap material e econômico, porém não equivalência arquitetural: Metalwork amplia rotas/material forms; Metallurgy adiciona Crucible/Faucet e cadeia própria.

## 9. Productive Metalworks
Productive Metalworks 1.15.1 também está presente. A coexistência cria risco de três providers oferecendo molten materials/crushed intermediates ou routes de alto rendimento. Tags, recipes e conversion ratios precisam ser auditados como sistema único.

## 10. Create Big Cannons
Big Cannons é uma integração potencial concreta porque está fisicamente instalado. Se Metalwork oferecer forms/recipes para materiais CBC, o provider CBC continua authority dos seus itens; Metalwork apenas fornece processamento. Não inferir cobertura de todo material CBC sem recipe/tag observada.

## 11. Dreams & Desires e Northstar
Ambos estão instalados e aparecem na superfície histórica de compatibilidade do projeto. A presença atual justifica smoke-test, mas não autoriza afirmar quais ores/metals da build 2.0.0 estão integrados sem inspeção runtime dos recipes.

## 12. Lithium crash — delta 2.0.0
O changelog exato da 2.0.0 registra **fix de crash com Lithium (#41)**. Lithium não foi encontrado como JAR top-level no pack atual; o fix permanece parte do artefato instalado, mas não é uma integração ativa a ser validada neste pack salvo mudança futura da modlist.

## 13. Datapacks e KubeJS
Em um pack com KubeJS, recipes de ore processing podem ser substituídas ou unificadas. Alterações devem manter conservation e impedir recipes paralelas antigas de sobreviverem junto da rota nova. `/reload` precisa reconstruir Recipe Manager sem stale viewer/cache.

## 14. Client/server
Recipe matching, item/fluid mutation e processing são server-authoritative. JEI, tooltips e fluid rendering são client-facing. Cliente não pode escolher output/rendimento diferente do recipe carregado pelo servidor.

## 15. Lifecycle
Testar recipe reload, chunk unload/restart durante processing, transfer de molten fluids, conversion por tags unificadas e mudança de datapack. Interromper uma operação não pode reaplicar input ou output na retomada.

## 16. Sobreposição e economia
O risco central do mod neste pack não é incompatibilidade binária, mas **multiplicação de rendimento**. Uma sequência que converte ore→crushed→molten→ingot por providers diferentes pode gerar ganho exponencial se ratios forem inconsistentes.

## 17. Riscos
1. Duas recipes equivalentes oferecem rendimentos muito diferentes.
2. Almost Unified escolhe variante canônica incompatível com recipe downstream.
3. Molten fluid de outro provider é aceito com amount semântico diferente.
4. Conversion loop cria ingots infinitos.
5. Chunk/restart duplica output de processing.
6. DnD/Northstar/CBC integration drift deixa recipes quebradas.
7. KubeJS remove uma rota, mas viewer/cache mantém apresentação antiga.
8. Lithium crash reaparece se o provider for adicionado futuramente.
9. Source antigo 1.20.1 é indevidamente tratado como implementação 2.0.0.

## 18. Matriz de testes
- [ ] Dedicated server inicia com Metalwork 2.0.0 + Create 6.0.10.
- [ ] Crushed ores da build aparecem com recipes válidas.
- [ ] Molten materials conservam tipo/amount ao transferir e solidificar.
- [ ] Rendimento por minério corresponde ao Recipe Manager atual.
- [ ] Almost Unified 1.4.2 não cria conversion loop.
- [ ] Coexistência com Metallurgy 1.0.3 e Productive Metalworks 1.15.1 não multiplica material indevidamente.
- [ ] CBC/DnD/Northstar paths presentes resolvem apenas recipes suportadas.
- [ ] `/reload` aplica mudanças de recipe sem stale cache.
- [ ] Restart durante processamento não duplica input/output.

Nenhum teste foi marcado como aprovado nesta auditoria documental.

## 19. Evidências e limites
A modlist física confirma JAR/mod id/runtime 2.0.0. A release oficial confirma NeoForge 1.21.1 Client & Server e fix de Lithium. A documentação oficial confirma novos fluids/crushed ores, maior rendimento e intenção de coexistência com outros molten-metal mods, incluindo recomendação de Almost Unified. O source público disponível não corresponde à linha 2.0.0 e não foi usado para inventar internals.

> 🔒 **Boundary canônico:** Metalwork amplia forms e recipes de minério; o Recipe Manager do servidor decide rendimento. Unificação de variantes nunca deve criar conversão lucrativa ou alterar conservation.
