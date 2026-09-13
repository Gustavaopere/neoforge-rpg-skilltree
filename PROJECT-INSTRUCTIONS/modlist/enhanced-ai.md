# Enhanced AI

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db8145be2ada23d50c7df3
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist 08.09.2026.txt` / `modlist.txt` — JAR confirmado fisicamente
- **Data da exportação:** 2026-09-10

## Propriedades do banco

- **Mod:** Enhanced AI
- **Arquivo JAR:** `enhancedai-4.2.3.0.jar`
- **Versão 1.21.1:** 4.2.3.0
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Mobs, RPG
- **Função:** Reformula e amplia comportamentos de IA de mobs, sobretudo hostis vanilla, com módulos configuráveis de perseguição, combate, anti-cheese, breaching/mining/sniping e outras respostas ao ambiente.
- **Dependências:** InsaneLib 2.4.32.0 está fisicamente presente e é a biblioteca exigida pela linha publicada do Enhanced AI. Runtime físico: Enhanced AI 4.2.3.0 / NeoForge 1.21.1.
- **Sobreposição:** Enhanced AI muda comportamento/IA; AI-Improvements reduz custo de processamento de IA. Epic Fight pode patchar entidades/combate. Essas superfícies se cruzam, mas não são equivalentes; validar ownership de goals e combat state.
- **Compatibilidade/Riscos:** Altera goals/comportamentos e pode aumentar dificuldade e custo de CPU. Modded mobs não devem ser assumidos como afetados automaticamente; a documentação atual orienta opt-in por Entity Type Tags quando aplicável. Cruzar com Epic Fight/entity patches, Alex's Mobs e outros AI mods por goal duplication, pathfinding excessivo, block interaction inesperado e double-processing. AI-Improvements 0.5.3 é otimização, não duplicata funcional.
- **Observações:** Runtime físico é 4.2.3.0; referências antigas 4.2.2.x foram removidas. A pesquisa pública deste ciclo confirmou o projeto/feature set, mas não recuperou um changelog oficial específico de 4.2.3.0; detalhes exclusivos dessa build não foram inventados.
- **Procedência:** Modlist física canônica de 08/09/2026 (595 top-levels) confirma `enhancedai-4.2.3.0.jar`, mod id `enhancedai`, versão 4.2.3.0 e InsaneLib 2.4.32.0. Documentação oficial do projeto sustenta o escopo funcional e o boundary de mobs vanilla/modded.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/enhanced-ai
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 09/09/2026 — Enhanced AI 4.2.3.0; behavior modules, vanilla/modded entity boundary, tags/config, server authority, AI-Improvements/Epic Fight overlap, lifecycle, risks and tests cataloged.
- **Histórico da decisão:**
- **Data da última decisão:** 2026-08-30

# Dossiê operacional — padrão Alex's Mobs

> **Runtime físico confirmado:** `enhancedai-4.2.3.0.jar` · mod id `enhancedai` · versão `4.2.3.0` · NeoForge 1.21.1. **InsaneLib 2.4.32.0** está presente. A pesquisa pública deste ciclo não recuperou changelog oficial específico da build 4.2.3.0; portanto o inventário abaixo separa o que é confirmado pelo runtime físico do feature set documentado do projeto.

## 1. Papel no modpack
Enhanced AI modifica a lógica comportamental de mobs para torná-los menos previsíveis e mais difíceis de explorar com estratégias simples. O projeto descreve o objetivo por exemplos como **Creepers breaching, Zombies mining e Skeletons sniping**, além de vários módulos configuráveis de reação/combate.

## 2. Authority / ownership
- **Minecraft/entity provider:** entidade, atributos-base, navigation e goals originais.
- **Enhanced AI:** goals/adaptações comportamentais que injeta e seus critérios/configuração.
- **Epic Fight:** combat/animation/entity-patch state quando um mob é patchado por esse framework.
- **AI-Improvements:** otimização de processamento; não define a dificuldade comportamental do Enhanced AI.

Não duplicar um mesmo goal/ataque em KubeJS ou mod próprio apenas porque o comportamento visual parece semelhante.

## 3. Escopo documentado
A documentação pública da linha atual lista módulos/áreas como:
- Anti-Cheese;
- Explosion Avoidance;
- Break Anger;
- Fishers;
- Custom Mob Fleeing;
- Pearlers;
- Biters;
- Item Disruption;
- Melee Attacking;
- Spawning;
- além das assinaturas de breaching/mining/sniping citadas pelo projeto.

Esses nomes representam superfícies configuráveis do projeto. Esta ficha não inventa valores, chances, ranges ou entidades específicas não confirmadas para 4.2.3.0.

## 4. Vanilla vs modded mobs
A documentação atual informa que, desde a linha v3, o foco padrão é **mobs vanilla**. Entidades modded podem ser incluídas em features relevantes por **Entity Type Tags**, conforme a documentação do projeto.

Consequência operacional: presença de Alex's Mobs, Cataclysm ou outro mob mod não prova que todo mob desses projetos recebe Enhanced AI. A tag/config efetiva é a autoridade.

## 5. Configuração
O projeto é fortemente configurável e apresenta o preset padrão como um aumento moderado de dificuldade, podendo ser ajustado para mais ou menos agressividade.

Antes de balancear o pack, auditar o arquivo de configuração gerado pela build física; não assumir defaults de uma versão anterior. Mudanças de config alteram gameplay e devem ser tratadas como decisão de pack.

## 6. Entity Type Tags
Tags de entity type são a superfície apropriada para opt-in/eligibility de mobs modded quando suportado por cada feature. Isso reduz a necessidade de hardcode por mod id.

Ao customizar, usar ResourceLocations reais e testar `/reload`/restart conforme o mecanismo de dados da build; uma tag malformada pode simplesmente deixar uma integração inativa sem crash evidente.

## 7. Perseguição e anti-cheese
O objetivo do mod inclui reduzir estratégias em que o jogador explora limitações previsíveis da IA. Isso pode mudar navigation, seleção de alvo e resposta ao ambiente.

Não confundir melhora de perseguição com aumento de atributos: velocidade, dano ou alcance devem ser atribuídos apenas quando o módulo/config correspondente realmente o fizer.

## 8. Interação com ambiente
Features como breaching/mining implicam que certos mobs podem responder a obstáculos de forma diferente do vanilla. Em um modpack com construções complexas, claims e máquinas, isso precisa de QA de proteção de blocos e de pathfinding.

Não afirmar que qualquer bloco modded é quebrável sem teste/config/tag correspondente.

## 9. Combate e Epic Fight
Epic Fight 21.17.3.1 está presente e pode substituir/patchar a camada de combate/animação de entidades. Enhanced AI e Epic Fight podem coexistir em níveis diferentes: um decide intenção/goal, o outro pode decidir moveset/combat patch.

O risco é dois providers tentarem controlar o mesmo ataque, target ou movement state. Testar mobs vanilla e mobs explicitamente patchados pelo Epic Fight.

## 10. AI-Improvements
`AI-Improvements 0.5.3` também está instalado. Seu papel é reduzir trabalho/custo de IA, enquanto Enhanced AI adiciona/expande comportamento. Não são duplicatas.

Regressão de performance deve medir TPS/CPU com os dois ativos, porque mais goals podem compensar parte do ganho de otimização.

## 11. Client / Server
Decisões de target, movement, block interaction, ataques e spawn são **server-authoritative**. Cliente renderiza o resultado e não deve decidir se um goal pode executar.

Dedicated server é o cenário de referência para validar compatibilidade; efeitos puramente visuais não devem ser usados como prova de execução da AI.

## 12. Lifecycle
Validar:
- spawn/despawn;
- aquisição/perda de alvo;
- chunk unload/reload;
- server restart;
- dimension change/portal quando aplicável;
- alteração de dificuldade;
- datapack/tag reload quando suportado;
- mudança de config;
- entidade entrar/sair de combate Epic Fight;
- mob persistente após relog dos jogadores.

## 13. Multiplayer
Com vários jogadores, target switching e pathfinding precisam convergir no servidor. Um mob não deve executar duas ações equivalentes por cada observador/client.

Testar dois jogadores atraindo o mesmo mob, logout do alvo, distância extrema e retorno ao chunk.

## 14. Riscos
1. goal duplicado com outro AI/combat mod;
2. pathfinding excessivo aumentar CPU;
3. mob modded incluído por tag sem suportar o comportamento esperado;
4. block breaking/mining afetar construção modded indevidamente;
5. target stale após logout/dimension change;
6. Epic Fight e Enhanced AI disputarem ataque/movement state;
7. AI-Improvements alterar timing e expor race/regressão;
8. config antiga ser reutilizada com semântica diferente;
9. spawn/behavior module ampliar dificuldade além do planejado;
10. assumir feature de versão pública anterior como detalhe exclusivo de 4.2.3.0.

## 15. Matriz de testes
1. Dedicated server boot com InsaneLib 2.4.32.0.
2. Zombies, Creepers e Skeletons em obstáculos/combate controlado.
3. Anti-cheese em arena reproduzível.
4. Dois jogadores alternando aggro.
5. Logout do alvo durante perseguição.
6. Chunk unload/reload com mob persistente.
7. Mob vanilla patchado pelo Epic Fight.
8. Mob modded somente após opt-in explícito por tag/config.
9. Testar bloco vanilla e bloco modded diante de features de interação ambiental.
10. Medir TPS/CPU com Enhanced AI + AI-Improvements.
11. Restart e revalidação de config/tags.
12. Atualização futura de Enhanced AI em instância de teste antes de promover ao pack.

**Esta catalogação não afirma que esses testes foram executados.**

## 16. Evidências
- modlist física canônica de 08/09/2026: JAR/mod id/versão 4.2.3.0, InsaneLib 2.4.32.0, AI-Improvements 0.5.3 e Epic Fight 21.17.3.1;
- CurseForge/GitHub oficiais do Enhanced AI: objetivo, feature set e configuração;
- wiki/projeto: foco vanilla desde v3 e opt-in de mobs modded via Entity Type Tags.

> **Boundary canônico:** Enhanced AI é authority apenas dos **comportamentos que injeta**. O entity provider mantém a entidade-base e Epic Fight mantém seu próprio combat framework quando aplicável.
