# Nyf's Spiders

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c969db9f0db8139b072d24db21f229b
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist 08.09.2026.txt` / `modlist.txt` — `nyfsspiders-neoforge-1.21.1-3.0.1.jar`, mod id `nyfsspiders`, runtime `3.0.1`, mixin `nyfsspiders.mixins.json`; Advanced Wall Climber API `1.0.2` confirmado somente em `META-INF/jarjar/` com `awcapi.mixins.json`
- **Data da exportação:** 2026-09-11

## Divergência documental detectada na exportação

- A página Notion declara procedência por uma `modlist.txt física canônica atual de 10/09/2026`. A authority física mais recente realmente acessível nesta execução permanece o snapshot de **08/09/2026 com 595 top-levels**. Nesse snapshot, Nyf's Spiders 3.0.1 e a AWC API 1.0.2 embarcada estão confirmados.

## Propriedades do banco

- **Mod:** Nyf's Spiders
- **Arquivo JAR:** `nyfsspiders-neoforge-1.21.1-3.0.1.jar`
- **Versão 1.21.1:** 3.0.1
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Mobs
- **Função:** Overhaul de navegação de spiders para crawling/perseguição mais realista em paredes e superfícies verticais usando Advanced Wall Climber API.
- **Dependências:** Advanced Wall Climber API 1.0.2 está embutida via JarJar na linha 3.x; não é entrada top-level separada. Requer NeoForge 1.21.1.
- **Sobreposição:** Não adiciona mobs novos nem substitui um overhaul geral de IA. Intersecta apenas a navigation/mobilidade de spiders; conflitos devem ser reproduzidos por entidade/path específico.
- **Compatibilidade/Riscos:** Altera navigation/pathfinding de spiders. Riscos: conflito com AI/navigation mods, lifecycle ordering, superfícies modded, orientação/hitbox e custo de pathfinding. AWC API 1.0.2 está embarcada no host.
- **Observações:** Runtime 3.0.1, file ID 7445048. 3.0.0 migrou para AWC API; 3.0.1 corrige especificamente Fabric. Mixin físico `nyfsspiders.mixins.json`.
- **Procedência:** modlist.txt física canônica atual de 10/09/2026 + CurseForge oficial 3.0.1/lineage 3.0.0 + metadata física do JarJar AWC API 1.0.2.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/nyfs-spiders
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 10/09/2026 — Nyf's Spiders 3.0.1 reconstruído: Advanced Wall Climber API 1.0.2 embedded, navigation/pathfinding, lifecycle, AI composition, difficulty, riscos e testes.
- **Histórico da decisão:**
- **Data da última decisão:** 2026-09-10

# Dossiê operacional — padrão Alex's Mobs

> **ESCOPO CANÔNICO.** Runtime físico: `nyfsspiders-neoforge-1.21.1-3.0.1.jar`, mod id `nyfsspiders`, versão `3.0.1`, NeoForge 1.21.1. A linha 3.x migrou para **Advanced Wall Climber API**; o JAR físico contém `awcapi-neoforge-1.21.1-1.0.2.jar` como JarJar interno. Essa API pertence ao host nesta instalação e não deve receber posição top-level própria.

## 1. Identidade e papel
- **Mod:** Nyf's Spiders.
- **JAR físico:** `nyfsspiders-neoforge-1.21.1-3.0.1.jar`.
- **Mod id:** `nyfsspiders`.
- **Runtime:** `3.0.1`.
- **Loader/jogo:** NeoForge 1.21.1.
- **Autor:** Nyfaria.
- **CurseForge project ID:** 686058.
- **Ambiente:** Client & Server.
- **Licença:** All Rights Reserved.
- **Papel:** reformular movimentação/navegação de spiders para crawling contínuo em paredes e outras superfícies, tornando perseguição vertical mais consistente.

## 2. O que muda no comportamento das spiders
Nyf's Spiders não é um mob pack: ele altera a **mobilidade/navegação** das aranhas. O objetivo publicado é fazer spiders se comportarem de modo mais convincente em superfícies verticais, em vez de depender apenas do comportamento vanilla simplificado de subida.

Isso afeta diretamente encontros em:
- cavernas;
- shafts e ravinas;
- construções com paredes altas;
- tetos/superfícies complexas suportadas pela implementação;
- arenas em que o jogador usa altura como defesa.

## 3. Advanced Wall Climber API
A linha 3.0.0 passa a usar **Advanced Wall Climber API**. A modlist física confirma a API 1.0.2 embutida sob `META-INF/jarjar`.

Regra de catálogo:
- `awcapi` não é mod top-level independente nesta modlist;
- não separar/updatear manualmente a cópia embutida sem conhecer o mecanismo de resolução do loader;
- falhas de climbing/navigation podem atravessar classes da API, mas ownership do comportamento final de spiders continua no Nyf's Spiders.

## 4. Release 3.0.1
O changelog exato da 3.0.1 registra **“Fix for Fabric”**. Portanto não há base para atribuir uma mudança funcional específica à build NeoForge além de ela pertencer à mesma linha 3.x já migrada para AWC API.

A mudança estrutural relevante para NeoForge vem da 3.0.0: adoção da Advanced Wall Climber API.

## 5. Pathfinding e navigation lifecycle
Alterar navegação é uma superfície invasiva de IA. O mod precisa decidir/ajustar como a entidade:
- escolhe caminho;
- muda orientação ao entrar/sair de paredes;
- contorna bordas;
- lida com blocos não completos;
- reage a knockback e queda;
- retoma perseguição depois de perder alvo.

Sem source exato pinado nesta auditoria, alvos de mixin, nós de path e algoritmos internos não são inventados.

## 6. Compatibilidade com outros mods de IA
O pack contém mods que também influenciam AI/pathfinding. O risco real não é “duas aranhas”, mas dois sistemas alterando navigation/goals da mesma entidade.

Um precedente histórico do próprio projeto é relevante: a linha 2.1.1 corrigiu crash quando outros mods definiam a navigation de spider cedo demais no lifecycle. Isso demonstra que **ordem de inicialização/substituição de navigation** é um regression gate legítimo.

Não declarar incompatibilidade atual sem reprodução; testar a composição física.

## 7. Escopo de entidades
A documentação pública fala em spiders. Esta ficha não presume automaticamente que toda entidade arachnid/modded herde o comportamento.

Para mobs adicionais, deve-se verificar:
- se herdam a classe/navigation esperada;
- se o mod os inclui explicitamente;
- se outro provider substitui sua AI.

Isso evita afirmar compatibilidade com criaturas de outros mods sem evidência.

## 8. Combate e dificuldade
A principal consequência de gameplay é reduzir zonas seguras verticais. Spiders podem alcançar jogadores em situações em que a IA vanilla falharia ou se comportaria de forma previsível.

Em conjunto com Epic Fight/AI mods, avaliar:
- alcance/telegraph de ataques;
- orientação do modelo ao atacar em parede;
- knockback derrubando a entidade de forma coerente;
- aggro/path recalculation em áreas estreitas.

O mod não deve receber ownership do dano base, loot ou atributos de spider que continuam pertencendo ao entity/provider correspondente.

## 9. Client/server
Navigation, alvo e posição efetiva são server-authoritative. O cliente precisa apenas receber/renderizar a entidade corretamente.

Riscos visuais podem surgir de rotação/orientação corporal durante crawling, mas uma pose incorreta não deve alterar hit/damage server-side.

## 10. Persistência e chunks
Spiders podem atravessar paredes/chunks durante perseguição. Testar:
- chunk unload/reload;
- relog próximo de uma spider em parede;
- restart com entidade persistida;
- transição entre chão e parede logo antes do save.

A ficha não afirma que o mod mantém estado persistente próprio; o objetivo é garantir que position/navigation sejam restaurados sem entidade presa ou teleportada indevidamente.

## 11. Riscos
1. **Navigation conflict:** outro mod substitui goals/navigation de spider.
2. **Lifecycle ordering:** precedente histórico de crash por navigation definida cedo.
3. **Path edge cases:** slabs, fences, trapdoors, paredes irregulares ou superfícies modded.
4. **Orientation/hitbox perception:** modelo pode parecer desalinhado durante crawling.
5. **Vertical difficulty:** muda balanceamento de bases/cavernas.
6. **Modded spiders:** cobertura não deve ser presumida sem teste.
7. **Embedded AWC API:** não catalogar nem atualizar separadamente.
8. **Performance:** path recalculation mais complexo pode aparecer com grande densidade de spiders.

## 12. Matriz de testes
- [ ] Dedicated server e cliente iniciam com Nyf's Spiders 3.0.1.
- [ ] AWC API 1.0.2 resolve como embedded sem duplicate-mod conflict.
- [ ] Spider vanilla persegue do chão para parede e retorna ao chão sem travar.
- [ ] Cave Spider executa navegação coerente.
- [ ] Testar corners, teto/saliência quando suportado pela build, slabs, fences e blocos não completos.
- [ ] Knockback durante climbing não duplica movimento nem prende entidade.
- [ ] Perda/recuperação de alvo recalcula path corretamente.
- [ ] Mod de AI/pathfinding ativo não reproduz crash de navigation lifecycle.
- [ ] Chunk unload/reload e restart preservam entidade em estado válido.
- [ ] Grupo grande de spiders não causa custo de tick desproporcional.

Nenhum teste foi marcado como aprovado nesta auditoria documental.

## 13. Evidências e limites
- Modlist física: JAR, mod id/runtime, `nyfsspiders.mixins.json` e AWC API 1.0.2 embarcada com `awcapi.mixins.json`.
- CurseForge oficial: project 686058, file ID 7445048, Release NeoForge 1.21.1 de 11/01/2026, Client & Server.
- Changelog 3.0.0: adoção da Advanced Wall Climber API; 3.0.1: fix específico para Fabric.
- Histórico oficial 2.1.1: correção de crash quando outros mods alteravam spider navigation cedo no lifecycle.
- **Limite:** algoritmo interno, cobertura de mobs modded e cada superfície escalável não foram inventados sem source/JAR exato.
