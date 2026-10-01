# Starbunclemania

> **Autoridade física atual — 27/09/2026.** `modlist(1).txt` contém 587 entradas top-level incluindo o modloader; este item ocupa a ordem física **#527**: JAR `starbunclemania-1.21.1-1.5.8.jar`, mod id `starbunclemania`, runtime `1.5.8`, SHA-1 `6af8bc4f9dc24c9ff4d49367ca37fd914822ceb6`.
- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1

## Propriedades do banco

- **Mod:** Starbunclemania
- **Arquivo JAR:** `starbunclemania-1.21.1-1.5.8.jar`
- **Versão 1.21.1:** 1.5.8
- **Estado no pack:** Integrado ao Github
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Magia, Automação
- **Função:** Addon de automação para Ars Nouveau: liquified Source, conversão Source↔fluidos, Place/Drain Fluid Glyph e novos jobs/acessórios de Starbuncles para item/fluid/energy transport, void e mount.
- **Dependências:** Ars Nouveau é dependência funcional principal. O addon integra com handlers externos de inventário/fluidos/energia. A feature de gas transport é específica de Mekanism e não implica necessidade local quando Mekanism não está presente.
- **Sobreposição:** Sobreposição parcial com automação de itens/fluidos de Ars/Create, mas por uma rota mágica distinta.
- **Compatibilidade/Riscos:** Jobs item/fluid/energy e liquified Source são transaction-sensitive. Riscos: dupe/loss, sided handler mismatch, stale jobs/chunks, filter errors e cross-mod handlers. Upstream 1.5.9 corrige o Starby Placer ignorando item metadata e sempre usando blockstate default.
- **Observações:** Runtime físico permanece 1.5.8. Upstream publicou **1.5.9** para NeoForge 1.21.1 em 24/09/2026; ela não está instalada.
- **Procedência:** modlist física atual + CurseForge oficial StarbuncleMania 1.5.8/1.5.9. A autoridade instalada permanece `starbunclemania-1.21.1-1.5.8.jar`.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/starbunclemania ; https://www.curseforge.com/minecraft/mc-mods/starbunclemania/files/8778598
- **Atualização/Status:** ATUALIZAÇÃO UPSTREAM REVALIDADA EM 01/10/2026 — StarbuncleMania físico permanece 1.5.8. A release **1.5.9** foi revisada e o fix de metadata/blockstate do Starby Placer foi incorporado abaixo.
- **Histórico da decisão:** 
- **Data da última decisão:** 2026-08-27

# Dossiê operacional — padrão Alex's Mobs

> **ESCOPO CANÔNICO.** Runtime físico: `starbunclemania-1.21.1-1.5.8.jar`, mod id `starbunclemania`, versão `1.5.8`. StarbuncleMania é um addon de **Ars Nouveau para automação mágica**, expandindo jobs de Starbuncles e criando pontes de Source↔fluido, itens, energia e outros recursos.
## 1. Identidade, versão e papel
- **Mod:** StarbuncleMania.
- **JAR físico:** `starbunclemania-1.21.1-1.5.8.jar`.
- **Mod id:** `starbunclemania`.
- **Versão:** `1.5.8`.
- **Loader/jogo:** NeoForge 1.21.1.
- **Ambiente:** Client & Server.
- **Decisão:** Sem decisão; preservada.
- **Papel:** ampliar o paradigma de automação do Ars Nouveau por workers/Starbuncles em vez de pipelines puramente tecnológicos.
## 2. Authority e ownership
- **Ars Nouveau:** authority de Source base, Starbuncles base, spell system e infraestrutura Ars.
- **StarbuncleMania:** authority dos seus jobs/acessórios, interfaces de transporte e mecanismos próprios de liquefação/conversão de Source.
- **Handlers externos de item/fluid/energy:** continuam authority de armazenamento e capability dos seus blocos.
O addon coordena transferências, mas não deve duplicar o state interno de tanks, batteries ou inventories externos.
## 3. Liquified Source
A documentação oficial confirma:
- conversão de **Source para fluido**;
- armazenamento desse fluido em tanks de outros mods ou tank do próprio addon;
- geração de Source a partir de fluidos, inclusive reconversão de liquified Source e uso configurável de lava;
- valores de conversão configuráveis.
Este é um ponto de integração direto com o ecossistema de fluid handling do pack e precisa ser testado contra tanks/transfer APIs atuais.
## 4. Place/Drain Fluid Glyph
O addon adiciona **Place/Drain Fluid Glyph** para spell turrets:
- pode preencher/consumir tanks adjacentes;
- pode operar com tank items compatíveis no inventário do jogador;
- a documentação explicitamente exclui buckets desse fluxo.
O glyph deve respeitar capacidade, tipo de fluido e transação do handler alvo sem dupe/loss.
## 5. Jobs/acessórios de Starbuncle
A lista oficial inclui:
### Side-Sensitive Item Transport — Wyrm Degree
Starbuncles aprendem a inserir/extrair itens de **um lado específico** do inventário, permitindo respeitar sided inventories/máquinas.
### Fluid Transport — Starbucket
Transporta fluidos de um tank para outro.
### Energy Transport — Starbattery
Transporta RF/energia de battery/generator para outro energy acceptor.
### Mekanism Gas Transport — Starballoon
Suporte publicado para gases padrão do Mekanism; pigments, sludges e material radioativo são explicitamente excluídos. O pack atual não deve assumir utilidade desse job sem Mekanism presente.
### Item Void — StarBin
Worker coleta itens do chão para descarte/void, com filtro recomendado para limitar o que pode ser removido.
### Mount
Acessório especial permite montar um Starbuncle gigante; o próprio upstream descreve esta parte como mais experimental/meme.
Os acessórios também podem ter uso cosmético/familiar conforme documentação.
## 6. Features planejadas ≠ implementadas
A página upstream lista **Fighter/Healer jobs** e integrações culinárias para liquified Source como planejadas. Elas **não são catalogadas como conteúdo disponível** nesta ficha até existirem em release/source confirmado.
## 7. Release 1.5.8
Changelog específico 1.5.8:
- corrige **Void Starby não respeitando filtros**;
- corrige outros Starbuncles **não indo para a cama atribuída**.
Esses dois comportamentos são regression gates diretos do runtime instalado.
## 8. Configuração
A documentação confirma valores configuráveis para conversão Source↔fluid/lava. A configuração física do pack não foi lida neste lote; portanto ratios, limites ou jobs habilitados não são afirmados.
Filtros de jobs, sided transport e assignments precisam ser tratados como state funcional persistente quando aplicável.
## 9. Client / server e multiplayer
- Servidor deve ser authority de transfers, consumo/produção de Source, inventários, energia, fluids, filtros e assignment de Starbuncles.
- Cliente renderiza workers, acessórios e feedback.
- Em multiplayer, dois jogadores observando o mesmo worker não podem disparar transferência dupla nem ver destinos diferentes como state definitivo.
## 10. Lifecycle
Validar:
- summon/criação e assignment de Starbuncle;
- equip/troca de job accessory;
- definir/alterar filtro;
- definir cama e retorno do worker;
- Source→fluid e fluid→Source;
- transfer entre tanks/inventories/energy handlers;
- chunk unload/reload com worker em trânsito;
- relog e server restart;
- destino removido ou lotado durante job;
- worker morto/removido durante transporte.
## 11. Integrações concretas no pack
- **Ars Nouveau:** dependência funcional principal.
- **Create/Ars Creo e automação Create:** sobreposição parcial de logística/processamento, mas paradigma e ownership distintos.
- **Tanks/fluids de Create e outros mods:** alvos naturais para Fluid Transport/liquified Source; validar capability compatibility.
- **Energy ecosystem do pack:** Starbattery precisa respeitar handlers instalados; não assumir suporte universal apenas por serem energéticos.
- **Mekanism Gas job:** feature publicada, mas não deve orientar decisão local se Mekanism não estiver instalado.
## 12. Riscos técnicos
1. **Dupe/loss de fluid/energy/item** em handler transacional não compatível.
2. **Sided inventory mismatch:** Wyrm Degree deve usar a face configurada corretamente.
3. **Void filtering:** 1.5.8 corrige exatamente o Void Starby ignorando filtros; teste obrigatório.
4. **Bed assignment:** 1.5.8 corrige retorno à cama atribuída.
5. **Chunk crossing:** worker/target em chunks diferentes pode deixar job stale.
6. **Cross-mod handlers:** item/fluid/energy APIs podem aceitar parcialmente a transferência.
7. **Experimental mount:** colisão/sync/movement precisam de regressão própria.
8. **Automation overlap:** duas rotas operando o mesmo inventário podem competir; isso não é necessariamente bug do addon.
## 13. Matriz de testes
- [ ] Dedicated server boot com Ars Nouveau + StarbuncleMania 1.5.8.
- [ ] Source→liquid e liquid→Source conservam quantidade conforme config.
- [ ] Lava→Source respeita valor configurado.
- [ ] Place/Drain glyph opera tank adjacente e tank item compatível; bucket continua fora do fluxo.
- [ ] Wyrm Degree respeita face de entrada/saída.
- [ ] Starbucket transfere fluido sem dupe/loss.
- [ ] Starbattery transfere energia sem overflow/dupe.
- [ ] StarBin com whitelist/filter não remove item fora do filtro.
- [ ] Worker retorna à cama atribuída após job.
- [ ] Chunk unload/reload durante transporte não duplica carga.
- [ ] Destino cheio/ausente não perde recursos.
- [ ] Mount sincroniza movimento em multiplayer.
Nenhum teste foi marcado como aprovado nesta auditoria.
## 14. Evidências
- Modlist física canônica 08/09/2026: JAR/mod id/versão.
- CurseForge oficial StarbuncleMania: Liquified Source, Place/Drain Fluid Glyph, jobs/accessories e escopo Client & Server.
- Changelog oficial 1.5.8: filtros do Void Starby e assigned bed fix.
## 15. Revalidação física — 11/09/2026
O runtime físico continua exatamente `starbunclemania-1.21.1-1.5.8.jar`, mod id `starbunclemania`, versão `1.5.8`. Ars Nouveau permanece presente como provider funcional principal.
Os fixes 1.5.8 de **Void Starby respeitar filtros** e **Starbuncles retornarem à cama atribuída** continuam sendo regression gates diretos. Fighter/Healer e outras planned features continuam fora do escopo factual. Nenhum teste runtime foi executado.
## 16. Revalidação física e upstream — 13/09/2026
O runtime físico permanece `starbunclemania-1.21.1-1.5.8.jar`, versão `1.5.8`, e a release 1.21.1 localizada mais recente continua 1.5.8. Os fixes de Void Starby respeitando filtros e de Starbuncles retornando à cama atribuída permanecem regression gates diretos; Fighter/Healer e demais planned features continuam excluídos do escopo factual. A decisão **Sem decisão** foi preservada. Nenhum job, conversão Source↔fluid ou teste de multiplayer foi executado nesta revalidação.


## 17. Atualização upstream 1.5.9 — não instalada
A autoridade física continua em **StarbuncleMania 1.5.8**.

A release **1.5.9** corrige o **Starby Placer** ignorando metadata do item e sempre colocando o **blockstate padrão**.

Impacto: itens que codificam variante/state precisam resultar no blockstate correspondente. O fix é relevante para automação porque perder metadata pode transformar um placement válido em variante incorreta sem necessariamente causar crash.

Gate de promoção 1.5.8→1.5.9: Starby Placer com blocos default e variantes; orientation/state properties; items com metadata/data components; refill/loop de placement; chunk unload/reload; multiplayer; confirmar consumo exactly-once e state final correto.

Fonte upstream: CurseForge file ID 8964554, StarbuncleMania 1.5.9 para NeoForge 1.21.1.