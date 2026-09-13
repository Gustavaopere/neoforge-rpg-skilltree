# Create: Rock & Stone

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db8118979cf9adbee08344
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist 08.09.2026.txt` / `modlist.txt` — JAR confirmado fisicamente
- **Data da exportação:** 2026-09-10

## Propriedades do banco

- **Mod:** Create: Rock & Stone
- **Arquivo JAR:** `create_rns-1.3.1-1.21.1-6.jar`
- **Versão 1.21.1:** 1.3.1-1.21.1-6
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Automação, Tecnologia, Worldgen
- **Função:** Provider de depósitos subterrâneos mineráveis por contraptions Create, com Miner Bearing, Mine Heads configuráveis, Deposit Scanner, depósitos vanilla/modded e integração data-driven para mineração contínua ou finita.
- **Dependências:** Create obrigatório. JEI/Jade e Xaero's World Map/JourneyMap são integrações opcionais documentadas; KubeJS possui plugin dedicado. 1.3.1 adiciona compatibilidade Create Aeronautics.
- **Sobreposição:** Compartilha domínio com outros sistemas de ore excavation/mineração renovável, mas owns seus depósitos, scanner, miner contraption e regras de depletion. Comparar economia/throughput concretos antes de classificar redundância.
- **Compatibilidade/Riscos:** Altera economia de recursos por depósitos e mineração automatizada. Riscos em worldgen density, claim/depletion, finite deposits, map markers, KubeJS startup config e moving sublevels. 1.3.1 adiciona Aeronautics, depósitos Coal/Lapis/Osmium/Platinum/Wolframite e regras Super Heavy para deposit blocks.
- **Observações:** runtime/filename 1.3.1-1.21.1-6. Deposit Scanner direciona ao depósito; Mine Head existe em tamanho normal e upgrade 3x3. O projeto oferece 4 datapack flavors de frequência e plugin KubeJS para selecionar deposits/spacing/separation.
- **Procedência:** Modlist física canônica de 08/09/2026 + runtime 1.3.1-1.21.1-6 + CurseForge oficial do projeto e changelog exato da release 1.3.1.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/create-rns
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 08/09/2026 — deposit/worldgen authority, Miner Bearing/Mine Head/Scanner, finite-resource accounting, KubeJS/datapack policy, map integrations, Aeronautics sublevels e regressões 1.3.1 catalogados.
- **Histórico da decisão:** Sem decisão formal. Em 08/09/2026, Create: Rock & Stone 1.3.1-1.21.1-6 foi reconfirmado como `Instalado`; contexto TFC legado foi removido e presença não foi convertida em decisão curatorial.
- **Data da última decisão:**

# Dossiê operacional — padrão Alex's Mobs

> ⛏️ Versão física confirmada: `create_rns-1.3.1-1.21.1-6.jar`, runtime `1.3.1-1.21.1-6`. O mod cria **depósitos subterrâneos** e uma cadeia própria de prospecção/mineração Create; a 1.3.1 adiciona suporte explícito a Create Aeronautics.

## 1. Papel e authority
Rock & Stone controla seus depósitos, discovery, claim/depletion e equipamentos de mineração. Create controla a cinética e as primitives de contraption. Mods de minério continuam owners dos próprios items; a presença de um ore pode apenas habilitar depósito compatível.

## 2. Miner Bearing
O **Miner Bearing** permite construir grandes mining contraptions e anexar componentes. Assembly/disassembly precisa preservar ownership do miner e não transformar attachments em máquinas independentes fora da contraption.

## 3. Mine Head
Mine Head é o núcleo de extração. Existe em tamanho normal e pode ser ampliado para **3x3** por formação específica. O tamanho influencia operação/rendimento conforme o state real; scripts não devem duplicar o multiplicador.

## 4. Deposit Scanner
O Deposit Scanner é ferramenta de prospecção: depois de sintonizado num depósito, as antenas piscam; ambas juntas indicam direção correta e maior frequência indica proximidade. É feedback sobre um depósito server/worldgen real, não geração de recurso pelo cliente.

## 5. Depósitos vanilla e modded
O projeto suporta depósitos vanilla e depósitos modded ativados automaticamente quando o ore correspondente existe. A lista/config ativa deve vir do datapack/KubeJS da instância; co-presença de um mod não autoriza inventar depósito se a build/config o desabilita.

## 6. Depósitos finitos
Jade pode mostrar recursos restantes quando finite deposits estão habilitados. Depletion precisa ser única e persistente: duas mining contraptions não podem extrair o mesmo saldo simultaneamente sem coordenação server-side.

## 7. Frequência de worldgen
O mod oferece **4 datapack flavors** para controlar frequência de depósitos. Em mundo existente, o upstream documenta ativação por `/datapack enable "create_rns:[flavor]"` seguida de restart. Mudança de flavor não retrogera automaticamente chunks antigos.

## 8. KubeJS plugin
Há plugin KubeJS dedicado para escolher depósitos e worldgen, incluindo spacing/separation. Startup scripts são policy do pack; mudanças após geração parcial podem criar regiões com distribuições distintas.

## 9. Map integrations
Xaero's World Map ou JourneyMap podem mostrar depósitos encontrados como ícones. O marcador é presentation/navigation; não deve criar depósito nem manter marcador válido depois que o state real deixou de existir sem revalidação.

## 10. JEI e Jade
JEI apresenta mining recipes/catalysts; Jade pode apresentar saldo de depósito finito. Ambos são interfaces de observação. Recipe/depletion state pertence ao servidor.

## 11. Create Aeronautics — 1.3.1
A 1.3.1 permite miners minerarem **de qualquer sublevel para qualquer sublevel**, aceita Deposit Scanner como filter em Navigation Table, marca deposit blocks como **Super Heavy** e impede assembly de physics contraptions com deposit blocks salvo quando movable deposits estão habilitados.

## 12. Novos depósitos — 1.3.1
O changelog adiciona Coal, Lapis (desabilitado por padrão), Osmium, Platinum e Wolframite. A disponibilidade efetiva depende da configuração/provider correspondente; a ficha não transforma o catálogo upstream em garantia de spawn no pack.

## 13. Claim e visualização
1.3.1 altera outline de deposit blocks claimed: aparece apenas com wrench ou miner blocks relevantes e recebe tint por miner claimant. Visual de claim deve refletir ownership server-side, não defini-lo.

## 14. Client/server e lifecycle
Worldgen, claim, depletion, extraction e inventories são server/common. Scanner animation, outlines, Ponder, JEI/Jade/maps são client-facing. Validar chunk load/unload, restart, sublevel assembly, config/datapack change e concorrência de miners.

## 15. Riscos
1. Depósito ser extraído duas vezes por miners concorrentes.
2. Mudança de datapack gerar seams de densidade.
3. KubeJS spacing/separation inválidos.
4. Scanner/marker apontar state stale.
5. Sublevel transform incorreto em Aeronautics.
6. Deposit block Super Heavy montar indevidamente.
7. Modded deposit ativar sem ore provider válido.
8. Economia de minério ficar excessivamente renovável.

## 16. Matriz de testes
1. Dedicated server boot.
2. Encontrar depósito com Scanner.
3. Mine Head normal versus 3x3.
4. Miner Bearing/attachments e conservation de output.
5. Finite deposit com dois miners concorrentes.
6. JEI/Jade e JourneyMap/Xaero quando presentes.
7. Troca de datapack flavor apenas em chunks novos.
8. KubeJS startup config em mundo de teste.
9. Aeronautics parent↔sublevel e sublevel↔sublevel.
10. Physics contraption com deposit block e movable deposits off/on.
11. Lapis default-disabled e depósitos modded realmente presentes.

## 17. Evidência
- modlist física 08/09/2026: 1.3.1-1.21.1-6;
- CurseForge oficial: Miner Bearing, Mine Head, Deposit Scanner, deposits, map/JEI/Jade integrations, 4 datapack flavors e KubeJS plugin;
- changelog 1.3.1: Aeronautics compatibility, Super Heavy deposits, new deposits, JEI catalyst UX, particles e claim outlines.

> 🔒 Boundary canônico: **Rock & Stone decide depósito/claim/depletion; Create decide a máquina cinética que o extrai**. Um saldo de depósito só pode ser consumido uma vez, inclusive entre sublevels.