# YUNG's Cave Biomes

> **Autoridade física atual — 28/09/2026.** Ordem física **#586**: JAR `YungsCaveBiomes-1.21.1-NeoForge-3.1.1.jar`, mod id `yungscavebiomes`, runtime `1.21.1-NeoForge-3.1.1`, SHA-1 `bcab81f578b3cb31e599ae327c6634b9fa8d0f04`.
- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1

## Propriedades do banco

- **Mod:** YUNG's Cave Biomes
- **Arquivo JAR:** `YungsCaveBiomes-1.21.1-NeoForge-3.1.1.jar`
- **Versão 1.21.1:** 1.21.1-NeoForge-3.1.1
- **Estado no pack:** Integrado ao Github
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Worldgen, Exploração
- **Função:** Adiciona biomas e decoração próprios às cavernas, criando ambientes subterrâneos temáticos sem assumir o papel principal de cave carving do YUNG's Better Caves.
- **Dependências:** YUNG's API 5.1.8. Complementar a YUNG's Better Caves 3.1.6; coexistência com terrain/biome providers atuais requer runtime QA.
- **Sobreposição:** Complementar ao Better Caves: Cave Biomes = biome/content/atmosfera; Better Caves = carving/hidrologia. Overlap com outros biome providers é de composição worldgen.
- **Compatibilidade/Riscos:** Riscos: biome/carver composition, structure intersections, visual fog/sandstorm divergence, mob/content density e duplicate discovery. Upstream declara compatibilidade ampla, mas o stack real exige worldgen QA.
- **Observações:** Mod id `yungscavebiomes`, runtime `1.21.1-NeoForge-3.1.1`. Frosted Caves e Lost Caves adicionam mobs, blocos, itens, música e conteúdo próprio; não substituem o carving do Better Caves.
- **Procedência:** modlist.txt física atual consultada em 13/09/2026 + CurseForge oficial YUNG's Cave Biomes NeoForge 3.1.1 revalidado em 13/09/2026 + YUNG's API 5.1.8 e Better Caves 3.1.6 físicos. A 3.1.1 continua a latest release 1.21.1; a 4.1.1 pertence ao Minecraft 26.1.2. Nenhum runtime worldgen/visual QA executado.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/yungs-cave-biomes
- **Atualização/Status:** PADRÃO ALEX'S MOBS REVALIDADO EM 13/09/2026 — YUNG's Cave Biomes 3.1.1 permanece exatamente instalado e continua a latest release NeoForge 1.21.1; Frosted/Lost Caves, biome-content authority, visual/server boundary e Better Caves composition preservados.
- **Histórico da decisão:** 
- **Data da última decisão:** 2026-09-07

# Dossiê operacional — padrão Alex's Mobs

> 🧊 **ESCOPO CANÔNICO.** Runtime físico: `YungsCaveBiomes-1.21.1-NeoForge-3.1.1.jar`, mod id `yungscavebiomes`, versão `1.21.1-NeoForge-3.1.1`. É conteúdo/biome provider subterrâneo vanilla+, complementar ao **Better Caves**: Cave Biomes define ambientes e conteúdo; Better Caves define principalmente carving/formas/hidrologia.
## 1. Conteúdo confirmado
A documentação oficial da linha 1.21.1 confirma dois cave biomes principais:
- **Frosted Caves**;
- **Lost Caves**.
Cada um adiciona conteúdo próprio, incluindo novo mob, blocos, itens, música e outras superfícies temáticas. Lost Caves também possui efeito visual de sandstorm/fog documentado pelo projeto.
## 2. Relação com Better Caves
YUNG's Cave Biomes não substitui YUNG's Better Caves 3.1.6. A divisão correta de authority é:
- **Better Caves:** cave carving, formas, rios/lakes/aquifers subterrâneos;
- **Cave Biomes:** biome identity, decoração, mobs, blocos, itens e atmosfera dos ambientes subterrâneos.
Os dois são complementares e podem coexistir.
## 3. Compatibilidade upstream
O projeto declara intenção de ser compatível com outros mods, inclusive worldgen mods. Isso é uma declaração de suporte upstream, não evidência de que todas as combinações do pack já foram testadas.
No stack atual, a interação mais importante é com Better Caves, Terralith, BYG/TerraBlender e estruturas subterrâneas.
## 4. Client/server e visual effects
A release é Client & Server. Biome placement, mobs e gameplay são server/world-authoritative; fog/sandstorm/music e outras superfícies visuais são client-facing.
Não usar ausência/presença de fog como prova de biome no servidor. Integrações devem consultar biome identity real.
## 5. Boundary para quests/perks
- Discovery deve usar registry identity do biome real.
- Entrar/sair repetidamente no mesmo biome não pode conceder progresso ilimitado.
- Mobs/loot/blocos do biome mantêm suas próprias identities; não atribuir automaticamente qualquer kill/coleta ao biome sem causalidade contextual real.
- Chunk generation não gera Mastery por si só.
## 6. Lifecycle crítico
Validar criação de mundo, chunks novos, biome placement, mob spawns, save/restart, dimension change, resource reload de assets visuais e coexistência com cave carving atual.
Mudanças de worldgen não são retroativas para chunks já gerados.
## 7. Riscos
1. **Biome/carver composition:** ambiente é cortado ou distribuído de forma anormal por outro provider.
2. **Structure intersection:** structures subterrâneas atravessam features críticas dos biomes.
3. **Visual divergence:** fog/sandstorm não renderiza em determinado renderer sem afetar a biome identity real.
4. **Mob/content density:** novos mobs/loot/blocks alteram economia e exploração subterrânea.
5. **Quest duplication:** biome discovery contabilizada por tick/chunk em vez de milestone deduplicado.
## 8. Matriz de testes
- [ ] Dedicated server inicia com Cave Biomes 3.1.1 + YUNG's API 5.1.8 + Better Caves 3.1.6.
- [ ] Frosted Caves e Lost Caves aparecem em chunks novos sem registry/data errors.
- [ ] Mobs/blocos/itens próprios registram e persistem corretamente.
- [ ] Better Caves carving não destrói sistematicamente a identidade/navegabilidade dos biomes.
- [ ] Terralith/BYG/TerraBlender coexistem sem crash ou placement impossível recorrente.
- [ ] Fog/sandstorm/music funcionam no cliente sem serem necessários para a lógica server-side.
- [ ] Quest discovery usa biome ID real e é exactly-once.
Nenhum teste foi marcado como aprovado nesta auditoria documental.
## 9. Evidências
- Modlist física atual: `YungsCaveBiomes-1.21.1-NeoForge-3.1.1.jar`, mod id `yungscavebiomes`.
- CurseForge oficial: release 3.1.1 NeoForge 1.21.1; Frosted Caves + Lost Caves; cada biome adiciona mob, blocos, itens, música e conteúdo próprio; intenção de compatibilidade ampla com worldgen mods.
## 10. Limitação
IDs internos dos biomes/mobs/features não foram extraídos do JAR nesta etapa. Qualquer integração provider-specific deve inspecionar registry/resources reais antes de depender de IDs ou hooks.
## 11. Revalidação física — 11/09/2026
A modlist física mantém exatamente `YungsCaveBiomes-1.21.1-NeoForge-3.1.1.jar`, mod id `yungscavebiomes`, versão `1.21.1-NeoForge-3.1.1`. A file page oficial continua apontando 3.1.1 como latest release NeoForge 1.21.1, publicada em 21/08/2025.
A release 4.1.1 publicada em 19/08/2026 pertence ao Minecraft `26.1.2`, portanto não substitui este runtime. YUNG's API `5.1.8` e Better Caves `3.1.6` continuam presentes. A divisão de authority permanece: Cave Biomes controla biome/content/atmosfera; Better Caves controla carving/hidrologia. O estado **Integrado ao Github** e a decisão **Sem decisão** foram preservados. Nenhum Frosted/Lost Caves placement, mob/content registration, fog/sandstorm/music, structure-intersection ou exactly-once discovery test foi executado nesta recatalogação.
## 12. Revalidação física e upstream — 13/09/2026
O runtime físico permanece `YungsCaveBiomes-1.21.1-NeoForge-3.1.1.jar`, versão `1.21.1-NeoForge-3.1.1`, e continua a latest release NeoForge 1.21.1 localizada. Frosted Caves e Lost Caves permanecem conteúdo/biome provider complementar ao Better Caves `3.1.6`, que continua authority de carving/hidrologia. A 4.1.1 disponível no projeto é da linha Minecraft 26.1.2 e não constitui update aplicável. Nenhum biome placement, mob/content registration, fog/sandstorm/music ou structure-intersection test foi executado nesta revalidação.
