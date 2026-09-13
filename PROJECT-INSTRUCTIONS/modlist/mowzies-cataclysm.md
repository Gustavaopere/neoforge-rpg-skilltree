# Mowzie's Cataclysm

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db819e97f4ca32c00d89e6
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist 08.09.2026.txt` / `modlist.txt` — `mowzies_cataclysm-1.2.2.jar`, mod id `mowzies_cataclysm`, runtime `1.2.2`; L_Ender's Cataclysm `3.33`, Mowzie's Mobs `1.8.2` e Integrated Mowzie's Mobs `1.3.0` confirmados fisicamente
- **Data da exportação:** 2026-09-11

## Divergência documental detectada na exportação

- A página Notion declara procedência por uma `modlist.txt física canônica atual de 10/09/2026`. A authority física mais recente realmente acessível nesta execução permanece o snapshot de **08/09/2026 com 595 top-levels**. Nesse snapshot, o stack necessário está confirmado.

## Propriedades do banco

- **Mod:** Mowzie's Cataclysm
- **Arquivo JAR:** `mowzies_cataclysm-1.2.2.jar`
- **Versão 1.21.1:** 1.2.2
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Compat, Exploração, RPG
- **Função:** Bridge de conteúdo entre Cataclysm e Mowzie's Mobs; 1.2.2 adiciona quatro Eyes para localizar bosses de Mowzie's Mobs. Music discs são apenas planejados.
- **Dependências:** Required oficiais: L_Ender's Cataclysm + Mowzie's Mobs. Ambos presentes no pack; Mowzie's Mobs físico é 1.8.2.
- **Sobreposição:** Não substitui Cataclysm nem Mowzie's Mobs. Intersecta exploração/worldgen com Integrated Mowzie's Mobs ao localizar bosses/estruturas, exigindo teste conjunto.
- **Compatibilidade/Riscos:** Bridge required por Cataclysm + Mowzie's Mobs. Risco principal é locator/worldgen mismatch com Integrated Mowzie's Mobs 1.3.0 e outros worldgen, além de shortcut de progressão e registry drift. Source oficial publicado está indisponível/404.
- **Observações:** Runtime 1.2.2, Release NeoForge 1.21.1 de 04/06/2026. Source link publicado aponta a repositório atualmente 404; internals não foram inferidos.
- **Procedência:** modlist.txt física canônica atual de 10/09/2026 + CurseForge oficial da release/description/relations. Source oficial indisponível no momento da auditoria.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/mowzies-cataclysm | https://www.curseforge.com/minecraft/mc-mods/mowzies-cataclysm/relations/dependencies
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 10/09/2026 — Mowzie's Cataclysm 1.2.2 reconstruído: 4 Eyes atuais, dependencies oficiais, boundary com providers/IMM, conteúdo planejado separado, source 404, riscos e testes.
- **Histórico da decisão:**
- **Data da última decisão:** 2026-09-10

# Dossiê operacional — padrão Alex's Mobs

> **ESCOPO CANÔNICO.** Runtime físico: `mowzies_cataclysm-1.2.2.jar`, mod id `mowzies_cataclysm`, versão `1.2.2`, NeoForge 1.21.1. A release oficial é de 04/06/2026. O projeto publica exatamente **quatro novos Eyes** para localizar bosses de Mowzie's Mobs; boss music discs são apenas conteúdo planejado. O link Source publicado atualmente resolve para um repositório GitHub indisponível/404, então internals não são inferidos.

## 1. Identidade e papel
- **Mod:** Mowzie's Cataclysm.
- **JAR físico:** `mowzies_cataclysm-1.2.2.jar`.
- **Mod id:** `mowzies_cataclysm`.
- **Runtime:** `1.2.2`.
- **Loader/jogo:** NeoForge 1.21.1.
- **Autor:** CyberRat2.
- **CurseForge project ID:** 1128348.
- **Licença:** All Rights Reserved.
- **Papel:** addon/bridge entre L_Ender's Cataclysm e Mowzie's Mobs para aproximar os dois sistemas por novo conteúdo de exploração.

## 2. Dependências oficiais
A página de relações lista exatamente duas required dependencies:
- **L_Ender's Cataclysm**;
- **Mowzie's Mobs**.

Ambos os providers estão presentes no pack. Mowzie's Mobs físico é 1.8.2; Cataclysm 3.33 e vários addons/bridges de Cataclysm também aparecem fisicamente na modlist.

**Ownership:** Mowzie's Cataclysm não substitui nenhum provider. Bosses, estruturas e combate continuam pertencendo aos mods-base; este addon fornece itens/ponte de localização.

## 3. Conteúdo atual confirmado: quatro Eyes
A descrição oficial 1.2.2 afirma que o mod adiciona **quatro novos Eyes para encontrar cada um dos bosses de Mowzie's Mobs**.

Isso transforma o addon em uma ferramenta de exploração/progressão:
- oferece rota deliberada de localização;
- reduz dependência de descoberta puramente aleatória;
- conecta a linguagem de Eyes associada a Cataclysm com alvos de Mowzie's Mobs.

A ficha não inventa nomes, recipes, consumo, dimension rules ou mapeamento Eye→boss sem source/JAR auditado que sustente esses detalhes.

## 4. Conteúdo planejado não é conteúdo instalado
A página pública menciona **boss music discs** como conteúdo planejado. Eles não devem ser catalogados como registrados/ativos na 1.2.2 apenas por aparecerem no roadmap textual.

Regra operacional: planejado ≠ implementado. Se uma versão futura adicionar os discs, a ficha deve ser atualizada contra o novo JAR/release.

## 5. Efeito sobre exploração e progressão
Mesmo sendo pequeno, o addon pode mudar significativamente o ritmo do pack:
- bosses antes encontrados por exploração podem se tornar objetivos localizáveis;
- quests podem apontar para Eyes em vez de coordenadas/compasses genéricos;
- worldgen de Integrated Mowzie's Mobs pode alterar onde estruturas aparecem, portanto os localizadores devem ser testados no worldgen final.

Isso é especialmente relevante porque o pack possui **Integrated Mowzie's Mobs 1.3.0**, que revampa/integra estruturas de Mowzie's Mobs.

## 6. Compatibilidade com Integrated Mowzie's Mobs
A modlist física contém `IMM v1.3.0-1.21.1.jar`. Esse mod altera a distribuição/integração de estruturas de Mowzie's Mobs.

Não há evidência nesta auditoria de que Mowzie's Cataclysm 1.2.2 tenha integração explícita com IMM. Portanto:
- não declarar incompatibilidade;
- não declarar compatibilidade especial;
- tratar a combinação como caso obrigatório de teste de localização.

Se os Eyes consultarem tags/structures que IMM substitui ou desloca, a função pode mudar; isso precisa ser observado em runtime.

## 7. Relação com Cataclysm
O addon usa Cataclysm como um dos providers requeridos, mas o conteúdo confirmado da 1.2.2 é voltado a localizar bosses de Mowzie's Mobs.

Não atribuir bosses, mobs, weapons ou structures de Cataclysm a este bridge. A dependência existe para integração/linguagem de conteúdo, não para transferência de ownership.

## 8. Client/server e persistência
O CurseForge não define Environment para o projeto. Como há itens funcionais que interagem com mundo/localização e duas dependencies server-side de conteúdo, o protocolo seguro é tratar a build como parte do conjunto normal client+server até metadata do JAR provar side diferente.

Sem source acessível, esta ficha não inventa:
- packet protocol;
- SavedData;
- capability;
- algoritmo de busca;
- cache de estruturas;
- estado persistente dos Eyes.

## 9. Source indisponível
O link Source atualmente publicado pelo projeto aponta para `CyberRat7/Mowzies_Cataclysm`, mas o destino retornou 404 durante esta auditoria.

Consequências:
- release/JAR físico são authority de identidade;
- descrição/relations CurseForge são authority de escopo público;
- internals não podem ser catalogados como confirmados;
- uma futura restauração/publicação do source deve permitir aprofundar registries, recipes e algoritmo dos Eyes.

## 10. Riscos
1. **Locator/worldgen mismatch:** Eyes podem não localizar corretamente estruturas alteradas por IMM/outros worldgen mods.
2. **Progression shortcut:** acesso cedo aos Eyes pode trivializar descoberta de bosses.
3. **Registry drift:** update de Mowzie's Mobs/Cataclysm pode mudar IDs esperados pelo bridge.
4. **Existing world:** estruturas ausentes em chunks antigos podem levar a busca longa/destinos inesperados.
5. **Dimension behavior:** não presumir dimension filtering sem teste.
6. **Source unavailable:** limita auditoria de recipes, tags e algoritmo.
7. **Planned-content confusion:** music discs ainda não são feature confirmada da 1.2.2.
8. **Addon stack:** Integrated Mowzie's Mobs altera o mesmo domínio de estruturas e exige regressão conjunta.

## 11. Matriz de testes
- [ ] Dedicated server e cliente iniciam com Mowzie's Cataclysm 1.2.2 + ambos providers.
- [ ] Os quatro Eyes aparecem no registry/JEI conforme build real.
- [ ] Recipe/obtenção de cada Eye funciona sem missing tag/item.
- [ ] Cada Eye encontra o boss/estrutura esperado em mundo novo.
- [ ] Testar mundo com Integrated Mowzie's Mobs 1.3.0 ativo.
- [ ] Testar existing world com chunks antigos e novos.
- [ ] Uso em dimensão incorreta falha de forma segura/coerente.
- [ ] Dois jogadores usando Eyes simultaneamente não geram estado inconsistente.
- [ ] Restart não altera targets de forma indevida.
- [ ] Confirmar que music discs planejados não são erroneamente usados em quests/recipes da 1.2.2.

Nenhum teste foi marcado como aprovado nesta auditoria documental.

## 12. Evidências e limites
- Modlist física: `mowzies_cataclysm-1.2.2.jar`, id/version exatos.
- CurseForge oficial: project 1128348, Release 1.2.2 NeoForge 1.21.1 de 04/06/2026.
- Descrição oficial: quatro Eyes atuais; boss music discs planejados.
- Relations oficiais: L_Ender's Cataclysm + Mowzie's Mobs required.
- Modlist física: Integrated Mowzie's Mobs 1.3.0 presente.
- **Limite:** source link oficial está indisponível/404; nenhuma classe/método/registry interno foi inventado.
