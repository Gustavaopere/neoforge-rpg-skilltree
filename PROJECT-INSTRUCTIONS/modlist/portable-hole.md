# Portable Hole

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c969db9f0db8132b128ed991fd90241
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist 08.09.2026.txt` / `modlist.txt` — `PortableHole-v21.1.0-1.21.1-NeoForge.jar`, mod id `portablehole`, runtime `21.1.0`; Puzzles Lib presente como dependência NeoForge
- **Data da exportação:** 2026-09-11

## Divergência documental detectada na exportação

- A página Notion declara procedência por uma “modlist.txt física canônica atual de 10/09/2026”. A autoridade física mais recente realmente acessível nesta execução permanece o snapshot de **08/09/2026 com 595 top-levels**. Nesse snapshot, Portable Hole 21.1.0 está presente. O corpo-fonte abaixo é preservado sem reescrita silenciosa.

## Propriedades do banco

- **Mod:** Portable Hole
- **Arquivo JAR:** `PortableHole-v21.1.0-1.21.1-NeoForge.jar`
- **Versão 1.21.1:** 21.1.0
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Exploração, QoL
- **Função:** Adiciona uma ferramenta que abre passagens temporárias direcionais através de blocos sólidos; os blocos retornam automaticamente após a duração configurada, permitindo atravessar paredes, pisos e tetos sem destruição permanente.
- **Dependências:** NeoForge 1.21.1; Puzzles Lib é dependência no alvo NeoForge; instalação Client & Server.
- **Sobreposição:** Ferramenta de travessia temporária; não substitui mineração, teleport ou sistema de claims/proteção.
- **Compatibilidade/Riscos:** Riscos: bypass de progressão/claims, restauração após chunk/restart, block entities elegíveis, fechamento com entidade dentro e configs extremas de profundidade/duração/cooldown. Feedback visual não é authority do state.
- **Observações:** Runtime 21.1.0, file ID 5733788, Release NeoForge 1.21.1 de 18/09/2024. Changelog exato: Port to Minecraft 1.21.1.
- **Procedência:** modlist.txt física canônica atual de 10/09/2026 + CurseForge oficial Portable Hole 21.1.0 + documentação oficial de mecânica/configuração.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/portable-hole
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 10/09/2026 — Portable Hole 21.1.0 reconstruído: túnel temporário, restauração, hardness/config, client-server, lifecycle, riscos e testes.
- **Histórico da decisão:**
- **Data da última decisão:** 2026-09-10

# Dossiê operacional — padrão Alex's Mobs

> **ESCOPO CANÔNICO.** Runtime físico: `PortableHole-v21.1.0-1.21.1-NeoForge.jar`, mod id `portablehole`, versão `21.1.0`, NeoForge 1.21.1. Portable Hole adiciona uma ferramenta de travessia temporária: abre um túnel direcional através de blocos e restaura o terreno depois. Puzzles Lib é dependência no alvo NeoForge.

## 1. Identidade e papel
- **Mod:** Portable Hole.
- **JAR físico:** `PortableHole-v21.1.0-1.21.1-NeoForge.jar`.
- **Mod id:** `portablehole`.
- **Runtime:** `21.1.0`.
- **Loader/jogo:** NeoForge 1.21.1.
- **Ambiente:** Client & Server.
- **Licença:** Mozilla Public License 2.0.
- **Papel:** criar passagens temporárias por paredes, pisos, tetos e outros blocos sólidos sem destruição permanente.
- **Decisão:** Sem decisão.

## 2. Mecânica central
O jogador usa a ferramenta sobre uma superfície e o mod remove temporariamente uma sequência curta de blocos na direção correspondente. A passagem existe por um período configurável e os blocos retornam automaticamente.

O uso altera travessia e acesso, mas não deve ser tratado como mineração permanente nem como substituto de ferramentas de quebra.

## 3. Restauração do terreno
A propriedade mais crítica é a restauração automática. Testar:
- bloco simples;
- bloco com block entity/inventário apenas se elegível pela configuração;
- passagem parcialmente obstruída durante a janela;
- chunk unload/reload;
- servidor restart enquanto uma abertura está ativa.

O estado final precisa convergir ao bloco original sem duplicação, perda ou overwrite indevido.

## 4. Direção e profundidade
A direção do túnel deriva da superfície clicada. A profundidade é configurável e afeta diretamente poder de exploração.

Valores altos podem trivializar paredes, estruturas e obstáculos. Balancear profundidade junto do cooldown e duração, não isoladamente.

## 5. Hardness e blocos afetáveis
O projeto publica limite de dureza e controle sobre blocos afetáveis. Isso é o principal boundary de segurança para impedir que a ferramenta atravesse conteúdo que deveria permanecer protegido.

Não presumir que todo bloco seja elegível. Config, tags e regras efetivas da instância precisam ser lidas antes de usar a ferramenta como requisito de quest.

## 6. Cooldown e duração
Cooldown e duração da passagem são configuráveis. Eles determinam frequência de uso e tempo disponível para atravessar.

Riscos de gameplay:
- cooldown muito baixo transforma barreiras em irrelevantes;
- duração muito longa facilita acesso persistente;
- duração curta pode prender jogador/entidade quando os blocos retornam.

## 7. Feedback visual
A descrição oficial menciona partículas/faíscas e bordas com efeito visual durante abertura/restauração. Esses efeitos são feedback, não authority do state do túnel.

Com shaders/PartiCull, ausência de partícula não significa que o bloco funcional não foi temporariamente alterado.

## 8. Dependência Puzzles Lib
No NeoForge, Puzzles Lib é dependência do mod. Não classificar Portable Hole como standalone removendo a library correspondente sem dependency graph.

Puzzles Lib continua owner de infraestrutura compartilhada; Portable Hole é owner desta mecânica específica.

## 9. Client/server e multiplayer
A alteração temporária de blocos precisa ser server-authoritative. Em multiplayer, dois clientes devem observar a mesma abertura e o mesmo momento de restauração.

Testar latência, jogador entrando na passagem no instante de fechamento e observadores em chunks adjacentes.

## 10. Lifecycle
Cobrir:
- uso normal;
- múltiplas aberturas consecutivas;
- relog;
- death/respawn;
- dimension change;
- chunk unload/reload;
- restart do servidor;
- `/reload` se configs/data relevantes forem recarregáveis.

A restauração não pode depender exclusivamente de state efêmero de cliente.

## 11. Integração com o pack
Superfícies relevantes:
- estruturas/worldgen densos;
- claims/proteção, se houver;
- mineração/progressão de ferramentas;
- Sable/contraptions: não presumir suporte a blocos em SubLevels sem teste;
- shaders/partículas apenas no feedback visual.

## 12. Riscos
1. **Bypass de progressão:** atravessar paredes pode contornar gates físicos.
2. **Restauração incorreta:** perda/overwrite de bloco após lifecycle incomum.
3. **Block entity state:** conteúdo complexo exige teste antes de considerar elegível.
4. **Player trapping:** fechamento com entidade dentro da passagem.
5. **Chunk lifecycle:** abertura ativa durante unload/restart.
6. **Claims/protection:** uso pode contornar regras de área se integração não respeitar eventos.
7. **Config extrema:** profundidade/duração/cooldown fora da curva.
8. **Visual-state confusion:** particles culladas não representam state funcional.

## 13. Matriz de testes
- [ ] Dedicated server e cliente iniciam com Portable Hole 21.1.0 + Puzzles Lib.
- [ ] Parede simples abre na direção correta e restaura integralmente.
- [ ] Piso e teto funcionam conforme direção clicada.
- [ ] Profundidade, duração, cooldown e hardness limit respeitam config real.
- [ ] Bloco não elegível permanece intacto.
- [ ] Chunk unload/reload durante abertura não perde restauração.
- [ ] Restart com abertura ativa converge para estado seguro.
- [ ] Dois clientes observam o mesmo state.
- [ ] Jogador dentro da passagem no fechamento não sofre state impossível/duplicação.
- [ ] Claims/estruturas protegidas, se aplicáveis, não são bypassadas indevidamente.

Nenhum teste foi marcado como aprovado nesta auditoria documental.

## 14. Evidências e limites
- Modlist física: JAR, mod id/runtime e mixins NeoForge/common.
- CurseForge oficial: project 682568, file ID 5733788, Release NeoForge 1.21.1 de 18/09/2024, Client & Server.
- Changelog 21.1.0: port para Minecraft 1.21.1.
- Descrição oficial: passagens temporárias, restauração automática e opções de profundidade, duração, cooldown, dureza, efeitos visuais e blocos afetáveis.
- **Limite:** configs locais e comportamento de block entities/claims não foram lidos nem testados; não foram inventados.
