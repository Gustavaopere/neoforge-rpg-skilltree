# Prometheus

## Propriedades do registro

- **Mod:** Prometheus
- **Arquivo JAR:** prometheus-neoforge-1.21-1.2.5.jar
- **Versão 1.21.1:** 1.2.5
- **Categoria:** Biblioteca, Compat
- **Decisão:** Dependência
- **Estado da pesquisa:** Verificado
- **Estado no pack:** Integrado ao Github
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/prometheus
- **Função:** Framework/biblioteca para tipos de fogo modded e comportamento de fogo extensível por outros mods.
- **Dependências:** NeoForge 1.21/1.21.1. Consumer causal Required e fisicamente presente: Soul Fire'd 6.1.0; a linha 6.x moveu a API de custom fire types para Prometheus.
- **Compatibilidade/Riscos:** Framework de fogo Client & Server com gameplay funcional. Riscos: consumer não mapeado, fire-type sync, propagação degradando para vanilla, dano/efeito duplicado, datapack drift e hooks de outros sistemas de fogo.
- **Sobreposição:** Infraestrutura para tipos de fogo customizados; não substitui um sistema completo de incêndio/spells por si só.
- **Observações:** Runtime 1.2.5. Soul Fire'd 6.1.0 está fisicamente presente e sua página canônica confirma Prometheus 1.2.5 como Required Dependency; a referência anterior a consumer não comprovado foi corrigida.
- **Procedência:** modlist(1).txt física reconferida em 25/09/2026 + CurseForge oficial Prometheus 1.2.5 + página canônica Soul Fire'd 6.1.0 + presença física do consumer.
- **Atualização/Status:** PADRÃO ALEX'S MOBS REVALIDADO EM 25/09/2026 — Prometheus 1.2.5/JAR físico reconfirmado e reclassificado para Dependência após confirmação física/canônica de Soul Fire'd 6.1.0 como consumer Required.
- **Histórico da decisão:** 2026-09-26 — reclassificado de Sem decisão para Dependência após confirmação de Soul Fire'd 6.1.0 como consumer Required de Prometheus 1.2.5.
- **Data da última decisão:** 2026-09-26

> **Autoridade física atual — 25/09/2026.** `modlist(1).txt` contém 587 entradas top-level incluindo o modloader; este item ocupa a ordem física #459: JAR `prometheus-neoforge-1.21-1.2.5.jar`, mod id `prometheus`, runtime `1.2.5`, SHA-1 `c4ef1020569a1dfc5bb161221449d7cf13b597cb`.

<callout icon="🔎" color="red_bg">
	**ESCOPO CANÔNICO.** Runtime físico: `prometheus-neoforge-1.21-1.2.5.jar`, mod id `prometheus`, versão `1.2.5`, NeoForge 1.21/1.21.1. Prometheus é uma API/framework para **tipos de fogo customizados** com comportamento funcional consistente. O projeto exige instalação Client & Server porque altera gameplay, não apenas render. `soul-fire-d-neoforge-1.21-6.1.0.jar` está fisicamente presente e sua página canônica confirma Prometheus 1.2.5 como Required Dependency; decisão **Dependência**.
</callout>
## 1. Identidade e papel
- **Mod:** Prometheus.
- **JAR:** `prometheus-neoforge-1.21-1.2.5.jar`.
- **Mod id:** `prometheus`.
- **Runtime:** `1.2.5`.
- **Loader/jogo:** NeoForge 1.21/1.21.1.
- **Ambiente:** Client & Server.
- **Licença:** custom license.
- **Papel:** API para registrar e integrar custom fire types.
- **Decisão:** Dependência.
## 2. Fire types como state funcional
Prometheus não é apenas recolor de chama. A documentação inclui dano, burning state e comportamento associado ao tipo de fogo. Portanto o servidor precisa conhecer o fire type funcional e seus efeitos.
## 3. Overlay, cor e dano
A API suporta overlay customizado, aparência de entidade em chamas e dano por segundo associado ao fire type. Visual e dano precisam permanecer ligados ao mesmo tipo; cor correta com dano incorreto é falha relevante.
## 4. Propriedades de fogo
Custom fires podem definir propriedades como light level, reação à chuva e associação com variantes de torch/campfire/lantern. Essas propriedades pertencem ao fire type registrado pelo consumer/datapack.
Não assumir valores do pack sem consumer/config específico.
## 5. Propagação por entidades/projéteis
A documentação destaca comportamento consistente para entidades e projéteis: zombies/arrows incendiados por um custom fire devem transmitir o tipo de fogo apropriado quando a mecânica assim determina.
Regression gate: não converter silenciosamente custom fire em fogo vanilla no primeiro hop.
## 6. Efeitos adicionais
Um custom fire pode aplicar efeito quando entidade recebe dano daquele fogo. O efeito concreto é definido pelo conteúdo/consumer; Prometheus fornece infraestrutura.
Testar duração, reaplicação e remoção no lado servidor.
## 7. Enchantments customizados
O framework publica suporte a Custom Fire Aspect e Custom Flame, de forma opcional/data-driven. A presença da API não prova que esses enchantments estejam registrados/ativos na instância.
Se um consumer os habilita, validar melee/projectile e compatibilidade com outros enchantment systems.
## 8. Datapacks e integrações
Prometheus permite custom fires e integrações por mods/datapacks. Isso amplia extensibilidade, mas também cria risco de ID/tag/config drift.
O pack físico atual contém `soul-fire-d-neoforge-1.21-6.1.0.jar`. A página canônica de Soul Fire'd confirma que, desde a linha 6.x, a API de custom fire types foi movida para Prometheus e que Prometheus 1.2.5 é Required Dependency. Copper Fire continua apenas exemplo upstream sem presença presumida.
## 9. Consumer mapping
A modlist física contém Soul Fire'd 6.1.0 e a página canônica desse consumer registra Prometheus 1.2.5 como Required Dependency.
Estado atual:
- framework presente = sim;
- consumer causal = Soul Fire'd 6.1.0;
- remoção isolada segura = não;
- decisão = `Dependência`.
## 10. Client/server e sync
Fire type, dano e efeitos precisam ser server-authoritative. Cliente recebe render/overlay correspondentes.
Testar multiplayer, late join, reconnect e entidade entrando em chunk de outro player sem trocar tipo de fogo indevidamente.
## 11. Lifecycle
Cobrir:
- ignição/extinção;
- chuva/água;
- dimension change;
- death/despawn;
- projectile impact;
- chunk unload/reload;
- datapack reload;
- consumer presence/absence.
Custom fire não pode persistir como state órfão após provider desaparecer.
## 12. Sobreposição
Prometheus não substitui mods completos de incêndio nem systems de spells. Ele é infraestrutura de fire types. Sobreposição real depende de consumers que registram fogo e de outros mods que interceptam burn/damage events.
## 13. Riscos
1. Consumer/API drift: Soul Fire'd 6.1.0 depende do contrato Prometheus 1.2.5; update ou remoção isolada pode quebrar o consumer.
2. Server/client divergindo no fire type.
3. Custom fire degradando para vanilla após propagação.
4. Dano/efeito duplicado por outros hooks de fogo.
5. Datapack ID/schema drift.
6. Rain/extinguish rules inconsistentes.
7. Enchantment integration competindo com outros sistemas.
8. Attribution error: bug do consumer tratado como bug da API.
## 14. Matriz de testes
- [ ] Dedicated server e cliente iniciam com Prometheus 1.2.5.
- [ ] Soul Fire'd 6.1.0 inicia com Prometheus 1.2.5 e registra seus custom fire types sem missing API/class.
- [ ] Custom fire representativo mantém cor/overlay e dano coerentes.
- [ ] Chuva/água respeitam propriedade definida.
- [ ] Entidade/projétil propaga o fire type esperado.
- [ ] Efeito adicional, se configurado, é aplicado uma vez conforme regra.
- [ ] Custom Fire Aspect/Flame, se ativos, funcionam server-side.
- [ ] Datapack reload não duplica fire types.
- [ ] Chunk/reconnect não troca custom fire por vanilla indevidamente.
Nenhum teste foi marcado como aprovado nesta auditoria documental.
## 15. Evidências e limites
- Modlist física: JAR, mod id/runtime e mixins common/NeoForge.
- CurseForge oficial: project 1365195, Release NeoForge 1.21/1.21.1 1.2.5, Client & Server.
- Descrição oficial: API para custom fires, overlay/damage/properties, propagação, efeitos e enchantments data-driven.
- Consumer causal comprovado: Soul Fire'd 6.1.0, fisicamente presente e documentado como Required de Prometheus 1.2.5.
- **Limite:** custom fire types/datapacks locais não foram enumerados; não foi inferida dependência a partir de exemplos upstream.
