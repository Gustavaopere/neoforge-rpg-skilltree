# Create Liquid Fuel

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3d369db9f0db81aca7a7ff433a8572cd
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist.txt` — 595 mods top-level
- **Data da exportação:** 2026-09-10

## Propriedades do banco

- **Mod:** Create Liquid Fuel
- **Arquivo JAR:** `createliquidfuel-3.0.0-1.21.1.jar`
- **Versão 1.21.1:** 3.0.0-1.21.1
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Manter
- **Categoria:** Tecnologia, Automação, Compat
- **Função:** Permite alimentar Blaze Burners do Create com combustíveis líquidos definidos por dados, incluindo suporte a fuel conditions, duração/heat tier e sincronização cliente-servidor.
- **Dependências:** Create 6.0.10 está fisicamente presente. NeoForge 1.21.1. Fuel entries são data-driven; outros fluid providers não são hard dependencies por si.
- **Sobreposição:** Integra fluids ao Blaze Burner; não substitui Create heat system nem providers dos fluidos. Sobreposição relevante ocorre se mais de um addon/datapack definir o mesmo combustível.
- **Compatibilidade/Riscos:** Riscos: fuel definitions conflitantes, heat/duration drift, stale data map após reload, fluids processuais queimáveis indevidamente e Create API drift. 3.0.0 corrige compat JSON scanning e sincronização de fuel data.
- **Observações:** JAR físico `createliquidfuel-3.0.0-1.21.1.jar`, mod id `createliquidfuel`, runtime 3.0.0-1.21.1. Release oficial NeoForge 1.21.1 de 05/09/2026.
- **Procedência:** modlist.txt física atual de 08/09/2026 + CurseForge/Modrinth oficiais Create: Liquid Fuel 3.0.0 e changelog exato.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/create-liquid-fuel
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 09/09/2026 — corpo vazio corrigido; Blaze Burner liquid fuel, Fluid data map, sync, conditions, compat scanning, lifecycle e testes catalogados para 3.0.0.
- **Histórico da decisão:**
- **Data da última decisão:** 2026-09-06

# Dossiê operacional — padrão Alex's Mobs

> 🔥 **ESCOPO CANÔNICO.** Runtime físico: `createliquidfuel-3.0.0-1.21.1.jar`, mod id `createliquidfuel`, versão `3.0.0-1.21.1`. O addon faz uma coisa central: permite alimentar **Blaze Burners do Create com combustíveis líquidos** definidos por dados.

## 1. Authority de combustíveis
Create continua owner do Blaze Burner, heat state e processamento térmico. Create Liquid Fuel fornece a camada que reconhece fluids elegíveis e traduz seu consumo em combustível do burner.
A presença do mod não torna qualquer fluido combustível. A lista/estatística depende dos entries data-driven e condições carregadas.

## 2. Datapack API e data map
A linha 3.0.0 substituiu o antigo scanning Gson de arquivos compat por um **Fluid data map sincronizado**. Isso é relevante para o pack porque fuel eligibility deixa de ser mero parsing local solto e passa a ter state de dados compartilhado com o cliente.
A release também adiciona suporte a `neoforge:conditions` nos fuel entries, permitindo ativação condicional via dados.

## 3. Sync cliente-servidor
3.0.0 corrige explicitamente a sincronização dos dados de liquid fuel para clients. O servidor deve continuar authority de consumo/heat; cliente pode renderizar informação coerente, mas não autorizar fuel ou superheating por conta própria.

## 4. Compat scanning
A 3.0.0 corrige crash causado por arquivos JSON não relacionados encontrados em `data/<namespace>/compat/`, passando a ignorá-los. Esse é um regression gate importante em um pack com muitos datapacks/addons.

## 5. Combustíveis e superheating
O changelog 3.0.0 restaura superheating para Garnished peanut oil. Isso prova que heat tier pode ser específico do entry/compat e deve ser lido do runtime, não inferido por nome do fluido.

## 6. Runtime atual do pack
Create físico: 6.0.10. Outros addons adicionam vários fluidos combustíveis/processuais; portanto o maior risco é **sobreposição de definição**, não a existência do addon em si. Um fluido deve ter uma política única de duração/heat no runtime final.

## 7. Lifecycle
Validar cold boot, datapack load, `/reload`, client join depois do reload, restart e mudança de packs. O data map e condições precisam convergir para o mesmo conjunto server/client sem duplicar entries.

## 8. Riscos
1. Fuel duplicado com duas definições conflitantes.
2. Heat tier/duração diferente entre server e UI.
3. `/reload` mantém data map stale.
4. Compat JSON de outro mod volta a causar crash/regressão.
5. Fluid processual vira combustível sem intenção de balanceamento.
6. Update Create altera Blaze Burner fuel hooks.

## 9. Boundary para quests/perks
Consumir combustível líquido não é milestone por tick. Qualquer progresso deve ser associado a uma ação causal deduplicável, como concluir um processo específico, e não ao burner permanecer alimentado.

## 10. Matriz de testes
- [ ] Dedicated server inicia com Create 6.0.10 + Liquid Fuel 3.0.0.
- [ ] Fluid configurado alimenta burner com duração/heat corretos.
- [ ] Fluid não configurado é rejeitado.
- [ ] `neoforge:conditions` habilita/desabilita entry conforme esperado.
- [ ] `/reload` atualiza dados sem stale cache.
- [ ] Cliente reconectado recebe o mesmo fuel data do servidor.
- [ ] Superheating não aparece em fluido sem entry correspondente.

Nenhum teste foi marcado como aprovado nesta auditoria documental.

## 11. Evidências e limite
CurseForge oficial confirma Release 3.0.0 NeoForge 1.21.1 de 05/09/2026, Fluid data map sincronizado, condições NeoForge, sync de dados e fixes de compat scanning/superheating. A config/datapack efetiva do pack não foi lida aqui; fuels realmente ativos permanecem authority do runtime.
