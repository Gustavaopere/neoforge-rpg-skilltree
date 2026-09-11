# Pickable Orbs

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3ca69db9f0db816d8cecd0351504de8b
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist 08.09.2026.txt` / `modlist.txt` — `pickable_orbs-1.21.1-1.0.0.jar`, mod id `pickable_orbs`, runtime `1.21.1-1.0.0`
- **Data da exportação:** 2026-09-11

## Divergência documental detectada na exportação

- A página Notion declara procedência por uma “modlist.txt física canônica atual de 10/09/2026”. A autoridade física mais recente realmente acessível nesta execução permanece o snapshot de **08/09/2026 com 595 top-levels**. Nesse snapshot, Pickable Orbs 1.0.0 está presente. O corpo-fonte abaixo é preservado sem reescrita silenciosa.

## Propriedades do banco

- **Mod:** Pickable Orbs
- **Arquivo JAR:** `pickable_orbs-1.21.1-1.0.0.jar`
- **Versão 1.21.1:** 1.21.1-1.0.0
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** RPG, QoL
- **Função:** Adiciona pickups em forma de orbes no estilo RPG que podem ser dropados por mobs/blocos configurados e aplicam benefícios imediatamente ao serem coletados, como cura ou efeitos de poção.
- **Dependências:** NeoForge 1.21.1. Nenhuma hard dependency externa adicional foi confirmada para a build física.
- **Sobreposição:** Não é sistema de XP nem item consumível armazenável; complementa combate/RPG com pickups imediatos. Clumps trabalha com XP orbs e não é substituto funcional.
- **Compatibilidade/Riscos:** Riscos: sustain/farm abuse por chance de drop, double pickup sob concorrência, effect stacking, entity buildup e config drift. Na linha 1.21.1 os orbes são entidades e a customização publicada é config-based; datapacks pertencem a 26.1.2+.
- **Observações:** Runtime 1.21.1-1.0.0. Defaults publicados incluem Healing, Poisonous, Damaging, Jumping, Speedster, Confusion, Levitation e Fire Resistance; valores efetivos permanecem configuráveis.
- **Procedência:** modlist.txt física canônica atual de 10/09/2026 + CurseForge oficial Pickable Health Orbs 1.21.1 + documentação oficial de entidades, tipos e configuração.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/pickable-orbs
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 10/09/2026 — Pickable Orbs 1.0.0 reconstruído: entity pickups, oito tipos publicados, config/drop model, server authority, lifecycle, balanceamento, riscos e testes.
- **Histórico da decisão:**
- **Data da última decisão:** 2026-09-10

# Dossiê operacional — padrão Alex's Mobs

> **ESCOPO CANÔNICO.** Runtime físico: `pickable_orbs-1.21.1-1.0.0.jar`, mod id `pickable_orbs`, versão `1.21.1-1.0.0`, NeoForge 1.21.1. O mod adiciona pickups RPG em forma de **entidades-orbe** que concedem benefícios imediatamente ao contato; nesta linha 1.21.1 a configuração é baseada em config, enquanto a migração para datapacks pertence apenas à linha 26.1.2+.

## 1. Identidade e papel
- **Mod:** Pickable Orbs / Pickable Health Orbs.
- **JAR físico:** `pickable_orbs-1.21.1-1.0.0.jar`.
- **Mod id:** `pickable_orbs`.
- **Runtime:** `1.21.1-1.0.0`.
- **Loader/jogo:** NeoForge 1.21.1.
- **Ambiente:** Client & Server.
- **Licença publicada:** All Rights Reserved.
- **Papel:** adicionar pickups imediatos de estilo RPG dropados por mobs/blocos configurados.
- **Decisão:** Sem decisão.

## 2. Entidades, não itens armazenáveis
Na linha instalada, os orbes são implementados como **entidades coletáveis**, não como itens para o jogador guardar e consumir depois.

Essa escolha afeta progressão e balanceamento:
- o benefício precisa ser decidido no momento da coleta;
- inventários/backpacks não devem capturar o pickup como item normal;
- despawn/collection radius e movimento da entidade tornam-se parte do lifecycle;
- automação que coleta itens não deve ser presumida compatível com essas entidades.

## 3. Drops configuráveis
A documentação publica configuração para definir:
- tipos de orbe disponíveis;
- chance de drop;
- mobs/fontes elegíveis;
- efeitos concedidos;
- duração e amplifier quando aplicável.

Assim, o comportamento efetivo do pack depende da config física. Esta auditoria não leu essa config e não declara chances/defaults customizados como ativos.

## 4. Healing Orb
O tipo de cura concede **cura direta**, com quantidade configurável. Como é heal imediato, testar interação com:
- max health elevado;
- absorção;
- efeitos de redução/aumento de cura;
- jogador já no máximo de vida;
- coleta simultânea de múltiplos orbes.

Não transformar o efeito visual do pickup em segundo heal via scripts/perks.

## 5. Orbes de efeitos publicados
A documentação da linha lista, entre os defaults publicados:
- Poisonous → Poison;
- Jumping → Jump Boost;
- Speedster → Speed;
- Confusion → Blindness;
- Levitation → Levitation;
- Fire Resistance → Fire Resistance;
- Damaging → Instant Damage.

O upstream cita durações default de 5 s para vários efeitos e 2 corações para o damaging orb, mas valores efetivos permanecem configuráveis e devem ser lidos da instância antes de balanceamento fino.

## 6. Coleta e server authority
A coleta deve ser server-authoritative para impedir divergência entre benefício visual e state real.

Validar:
- dois jogadores disputando o mesmo orbe;
- pickup ocorrendo uma única vez;
- latência;
- jogador morto/desconectado durante contato;
- mob/orbe descarregado por chunk;
- efeito enviado corretamente ao cliente após coleta.

## 7. Relação com Clumps e XP
O pack também possui Clumps, que trabalha com **XP orbs**. Pickable Orbs é outro sistema:
- seus pickups concedem benefícios configurados;
- não são automaticamente XP orbs;
- otimização/agregação de XP não deve ser assumida sobre as entidades deste mod.

Qualquer colisão só pode ser afirmada se o runtime mostrar que ambos tocam a mesma entity class/event.

## 8. Integração com RPG/combate
O mod cria uma camada de sustain/recompensa momentânea após combate. Isso pode cruzar:
- Epic Fight e dificuldade de mobs;
- lifesteal/healing de outros mods;
- buffs de poções;
- progression/perks que aumentam duração/amplifier;
- farm de mobs.

O principal risco de balanceamento é uma fonte configurada produzir pickups em frequência que trivialize dano/consumíveis.

## 9. Config versus datapack
A documentação atual do projeto informa que **a partir de 26.1.2+** os dados foram movidos para datapacks.

Isso não deve ser retroprojetado para a build 1.21.1 instalada. Para `1.21.1-1.0.0`, tratar a customização publicada como config-based até evidência física em contrário.

## 10. Lifecycle das entidades
Testar:
- spawn após kill/break configurado;
- pickup;
- despawn natural;
- chunk unload/reload;
- dimension change;
- restart;
- água/lava/void quando aplicável;
- múltiplos orbes sobrepostos.

Nenhum orbe deve duplicar benefício após save/reload ou ser coletado duas vezes.

## 11. Riscos
1. **Balanceamento:** chance de drop pode fornecer sustain excessivo.
2. **Double pickup:** concorrência/latência não pode aplicar efeito duas vezes.
3. **Effect stacking:** vários orbes podem renovar/empilhar buffs de modo inesperado.
4. **Farm abuse:** mob farms podem converter kills em cura/buffs em escala.
5. **Entity load:** muitos drops não coletados podem pressionar tick/render.
6. **Config drift:** update pode alterar schema/defaults.
7. **Wrong version assumptions:** datapack system 26.1.2+ não pertence à linha 1.21.1.
8. **Item/entity confusion:** storage mods não devem ser considerados consumers automaticamente.

## 12. Matriz de testes
- [ ] Dedicated server e cliente iniciam com 1.0.0.
- [ ] Fonte configurada gera orbe segundo chance real da instância.
- [ ] Healing Orb cura uma vez e respeita max health.
- [ ] Poison/Speed/Jump/Blindness/Levitation/Fire Resistance aplicam duração/amplifier configurados.
- [ ] Damaging Orb aplica dano uma única vez.
- [ ] Dois players não conseguem consumir o mesmo orbe duas vezes.
- [ ] Múltiplos orbes simultâneos seguem regras vanilla/config de stacking.
- [ ] Chunk unload/reload não duplica pickups.
- [ ] Farm com muitos mobs não cria entity buildup descontrolado.
- [ ] Clumps/XP collection não altera indevidamente estes pickups.

Nenhum teste foi marcado como aprovado nesta auditoria documental.

## 13. Evidências e limites
- Modlist física: `pickable_orbs-1.21.1-1.0.0.jar`, mod id/runtime exatos.
- CurseForge oficial: project 554992, Release NeoForge 1.21.1 de 16/08/2026, Client & Server.
- Página oficial: pickups como entidades; drops por mobs/blocos; tipos/chances/efeitos/duração/amplifier configuráveis e oito tipos default publicados.
- Documentação atual: datapacks somente a partir de 26.1.2+; não aplicado retroativamente à build 1.21.1.
- **Limite:** config física do pack não foi lida; chances, fontes, duração/amplifier e tipos habilitados localmente não foram inventados.
