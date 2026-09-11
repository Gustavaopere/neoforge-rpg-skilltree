# Prometheus

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db8183ae7fe40157da74d7
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist 08.09.2026.txt` / `modlist.txt` — `prometheus-neoforge-1.21-1.2.5.jar`, mod id `prometheus`, runtime `1.2.5`; `soul-fire-d-neoforge-1.21-6.1.0.jar` também está fisicamente presente
- **Data da exportação:** 2026-09-11

## Divergências documentais detectadas na exportação

- A página Notion declara procedência por uma “modlist.txt física canônica atual de 10/09/2026”. A autoridade física mais recente realmente acessível nesta execução permanece o snapshot de **08/09/2026 com 595 top-levels**.
- O corpo do dossiê afirma corretamente que a menção upstream a Soul Fire'd não basta para inferir presença/dependência; porém, a modlist física acessível **confirma a presença de Soul Fire'd 6.1.0**. Essa presença, isoladamente, ainda não prova que Soul Fire'd seja consumer causal de Prometheus. O corpo-fonte abaixo é preservado sem reescrita silenciosa.

## Propriedades do banco

- **Mod:** Prometheus
- **Arquivo JAR:** `prometheus-neoforge-1.21-1.2.5.jar`
- **Versão 1.21.1:** 1.2.5
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Biblioteca, Compat
- **Função:** Framework/biblioteca para tipos de fogo modded e comportamento de fogo extensível por outros mods.
- **Dependências:** NeoForge 1.21/1.21.1. Nenhum consumer causal instalado foi comprovado neste passe; necessidade permanece dependente de consumer/datapack real.
- **Sobreposição:** Infraestrutura para tipos de fogo customizados; não substitui um sistema completo de incêndio/spells por si só.
- **Compatibilidade/Riscos:** Framework de fogo Client & Server com gameplay funcional. Riscos: consumer não mapeado, fire-type sync, propagação degradando para vanilla, dano/efeito duplicado, datapack drift e hooks de outros sistemas de fogo.
- **Observações:** Runtime 1.2.5. O upstream cita Soul Fire'd/Copper Fire como exemplos de consumers, mas isso não foi usado para afirmar presença no pack.
- **Procedência:** modlist.txt física canônica atual de 10/09/2026 + CurseForge oficial Prometheus 1.2.5 + cruzamento atual do catálogo.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/prometheus
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 10/09/2026 — Prometheus 1.2.5 reconstruído: custom fire API, damage/overlay/properties, propagation, effects/enchantments, consumer mapping, lifecycle, riscos e testes.
- **Histórico da decisão:**
- **Data da última decisão:** 2026-09-10

# Dossiê operacional — padrão Alex's Mobs

> **ESCOPO CANÔNICO.** Runtime físico: `prometheus-neoforge-1.21-1.2.5.jar`, mod id `prometheus`, versão `1.2.5`, NeoForge 1.21/1.21.1. Prometheus é uma API/framework para **tipos de fogo customizados** com comportamento funcional consistente. O projeto exige instalação Client & Server porque altera gameplay, não apenas render. Nenhum consumer causal instalado foi comprovado neste passe; decisão permanece **Sem decisão**.

## 1. Identidade e papel
- **Mod:** Prometheus.
- **JAR:** `prometheus-neoforge-1.21-1.2.5.jar`.
- **Mod id:** `prometheus`.
- **Runtime:** `1.2.5`.
- **Loader/jogo:** NeoForge 1.21/1.21.1.
- **Ambiente:** Client & Server.
- **Licença:** custom license.
- **Papel:** API para registrar e integrar custom fire types.
- **Decisão:** Sem decisão.

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

O catálogo atual não comprovou consumer instalado específico. A menção upstream a Soul Fire'd/Copper Fire não significa que esses mods estejam no pack.

## 9. Consumer mapping
Busca no catálogo encontrou apenas Prometheus e uma referência genérica em Cobweb. Não houve página instalada provando dependência causal desta build.

Fail-closed:
- framework presente = sim;
- consumer causal = não demonstrado;
- remoção segura = não demonstrada;
- decisão = `Sem decisão`.

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
1. Consumer não mapeado/remoção insegura.
2. Server/client divergindo no fire type.
3. Custom fire degradando para vanilla após propagação.
4. Dano/efeito duplicado por outros hooks de fogo.
5. Datapack ID/schema drift.
6. Rain/extinguish rules inconsistentes.
7. Enchantment integration competindo com outros sistemas.
8. Attribution error: bug do consumer tratado como bug da API.

## 14. Matriz de testes
- [ ] Dedicated server e cliente iniciam com Prometheus 1.2.5.
- [ ] Dependency scan identifica consumers reais.
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
- Busca atual do catálogo não comprovou consumer causal instalado.
- **Limite:** custom fire types/datapacks locais não foram enumerados; não foi inferida dependência a partir de exemplos upstream.
