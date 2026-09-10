# Sable: True Impact

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db813c9a9cd280c942aa67
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist.txt` — 595 mods top-level
- **Data da exportação:** 2026-09-10

## Propriedades do banco

- **Mod:** Sable: True Impact
- **Arquivo JAR:** `true_impact-0.5.7-delta.jar`
- **Versão 1.21.1:** 0.5.7-delta
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Manter
- **Categoria:** Compat, Tecnologia, RPG
- **Função:** Sistema experimental de dano físico por colisão para estruturas Sable, com terrain destruction, material-aware strength, cumulative cracking, early fracture e opções configuráveis de dano/fratura/performance.
- **Dependências:** Sable 2.0.5 é base física. Create 6.0.10 e Create Aeronautics 1.3.2 têm integração parcial/planejada segundo upstream; Create Tracks+ 1.0.6b6 é interação crítica no pack.
- **Sobreposição:** Complementa Sable/Aeronautics com dano/fratura. Qualquer outro sistema de colisão/dano físico deve ser auditado para impedir double-processing.
- **Compatibilidade/Riscos:** ALTO RISCO/EXPERIMENTAL: destruição de terreno é intencional; upstream recomenda backups e configs conservadoras. Limitações incluem força imperfeita em grandes estruturas, stress/fatigue incompletos, performance e compatibilidade parcial com mods Create. Testar static-contact e drop duplication.
- **Observações:** mod id `true_impact`; runtime 0.5.7-delta Beta, atual para NeoForge 1.21.1. Decisão Manter preservada. Estado de pesquisa continua Verificado: risco experimental não é incerteza de identidade.
- **Procedência:** Runtime/JAR e stack: modlist física canônica 08/09/2026. Features, limitações e recomendações de backup: documentação oficial Sable: True Impact.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/sable-true-impact
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 09/09/2026 — True Impact 0.5.7-delta; impact damage, material/cracking/fracture, Sable/Aeronautics/Tracks+, performance, riscos e testes catalogados.
- **Histórico da decisão:** 2026-09-06 — decisão fechada em Manter. 0.5.7-delta é a Beta atual e o projeto é explicitamente experimental; risco de dano ao mundo e comportamento de física permanece documentado como condição de uso.
- **Data da última decisão:** 2026-09-06

> 💥 **ESCOPO CANÔNICO.** Runtime físico: `true_impact-0.5.7-delta.jar`, mod id `true_impact`, versão `0.5.7-delta`. Sable: True Impact é um **mod experimental de dano físico por colisão** para estruturas Sable, com integração parcial/planejada a Create e Create Aeronautics. Impactos de alta energia podem danificar terreno, acumular cracking e iniciar fratura estrutural. O upstream recomenda backup e testes conservadores antes de survival/server.

## 1. Identidade, versão e decisão
- **Mod:** Sable: True Impact.
- **JAR:** `true_impact-0.5.7-delta.jar`.
- **Mod id:** `true_impact`.
- **Versão:** `0.5.7-delta`.
- **Minecraft/loader:** NeoForge 1.21.1.
- **Ambiente:** Client & Server.
- **Canal:** Beta; build pública mais recente da linha delta em 14/07/2026.
- **Decisão vigente:** **Manter**; preservada.

## 2. Authority e ownership
- **Sable 2.0.5:** authority da estrutura física e transforms base.
- **True Impact:** authority das regras adicionais de damage/cracking/fracture causadas por impacto quando aplicáveis.
- **Create/Aeronautics:** continuam authority de suas contraptions/componentes e lógica própria.
True Impact não deve ser tratado como physics engine substituto de Sable.

## 3. Impact damage baseado em física
O projeto declara **physics-based impact damage for Sable structures**. A intensidade do resultado considera impacto/força e regras configuradas, em vez de um simples evento de colisão binário.
Sem source/config pin da 0.5.7, a ficha não registra fórmula exata de energia, threshold ou multiplicadores.

## 4. Terrain destruction
Colisões físicas de alta força podem **destruir terreno por design**.
Isso transforma qualquer teste de veículo em potencial operação destrutiva sobre o save. O upstream recomenda backups explícitos e uso inicial em mapas de teste.
Claims/proteções e world safety do modpack devem ser validados: não presumir que um mod de proteção intercepte automaticamente dano físico de True Impact.

## 5. Material-aware strength
A descrição atual confirma lógica de **material-aware block strength**. Materiais diferentes podem resistir de forma distinta aos impactos.
Não inferir tabela concreta de resistência. Testar amostras representativas do pack, incluindo blocos vanilla, Create, decorativos e componentes de veículo.

## 6. Cumulative cracking
True Impact possui **cumulative cracking before blocks break**. Assim, um bloco pode acumular dano entre impactos antes de ser destruído.
Regression gates:
- crack state não deve duplicar após save/reload;
- dano acumulado precisa desaparecer/persistir conforme contrato real;
- cliente deve renderizar feedback coerente com state do servidor;
- milhares de blocos rachados não devem causar custo desproporcional.

## 7. Early fracture behavior
O projeto também confirma **early physical-structure fracture behavior**. Esta camada ainda não equivale a uma simulação completa de stress interno.
A própria documentação lista como limitações atuais:
- transmissão de força imperfeita em estruturas grandes;
- internal stress simulation inacabada;
- long-term material fatigue incompleta.
Portanto fratura é feature experimental, não solver estrutural maduro.

## 8. Impact sampling e contato estático
A descrição atual registra:
- sampling melhorado para impactos em grandes superfícies;
- proteção contra **static-contact self-destruction** na maioria dos casos comuns.
Testar veículo parado sobre terreno, pouso suave, contato prolongado e múltiplos pontos de contato para detectar dano fantasma.

## 9. Configuração
O upstream confirma opções configuráveis de:
- damage;
- fracture;
- performance.
A configuração física do pack não foi lida. Nenhum threshold atual é afirmado.
Para survival/server, o próprio autor recomenda começar com valores conservadores e testar em backup.

## 10. Create e Create Aeronautics
A integração é descrita como **planned and partial** para Create/Create Aeronautics, com early support para dynamic structure interactions.
O pack possui Create `6.0.10` e Create Aeronautics `1.3.2`. Isso exige fail-closed:
- uma estrutura Aeronautics pode ser testada;
- não assumir que todo block/component Create transmite dano/fratura corretamente;
- bugs de compatibilidade com alguns Create-related mods são limitação oficial conhecida.

## 11. Create Tracks+
O pack possui Create Tracks+ `1.0.6b6`. Tracks/suspensão geram contatos frequentes com terreno, portanto são uma integração crítica:
- contato normal não deve causar autodestruição;
- impacto real de alta energia pode causar dano conforme config;
- quebra de track/mount não deve duplicar drops;
- fratura não deve deixar physics objects órfãos.

## 12. Performance
Impact sampling, material lookup, cracking acumulativo e fracture checks podem ser caros em estruturas grandes.
Stress tests devem variar:
- área de contato;
- velocidade;
- massa/tamanho do objeto;
- número de blocos danificáveis;
- colisões simultâneas;
- multiplayer observers.
A configuração de performance deve ser registrada quando o arquivo local for auditado.

## 13. Client / server e multiplayer
Damage e world destruction precisam ser server-authoritative. Clientes podem renderizar cracks/feedback, mas não decidir quais blocos quebram.
Em multiplayer validar:
- observadores veem o mesmo dano;
- colisão não é aplicada duas vezes por cada cliente;
- reconnect reconstrói state visual;
- lag não multiplica eventos de impacto.

## 14. Known limitations upstream
A documentação atual reconhece:
- edge cases inesperados;
- transmissão de força imperfeita em grandes estruturas;
- stress interno inacabado;
- material fatigue incompleta;
- possíveis incompatibilidades com mods relacionados a Create ou modpacks grandes.
Esses são limites declarados do projeto, não bugs locais confirmados.

## 15. Lifecycle
Validar:
- world/server boot;
- spawn/assemble de estrutura Sable;
- impacto simples e repetido;
- crack accumulation;
- chunk unload/reload com dano parcial;
- server restart;
- fracture durante movimento;
- dismantle/break de estrutura danificada;
- colisão em boundary de chunk;
- recovery de mundo após crash durante impacto.

## 16. Riscos técnicos
1. **Destruição intencional de mundo:** exige backup.
2. **Experimental/Beta:** comportamentos inesperados são explicitamente esperados.
3. **Partial Create integration:** não há cobertura universal de addons.
4. **Static-contact false positives:** mitigados, mas não garantidamente eliminados.
5. **Performance spikes:** impactos grandes podem tocar muitos blocos.
6. **Fracture state corruption:** estrutura pode fragmentar em situação não prevista.
7. **Drop duplication/loss:** componentes quebrados durante physics precisam ser atômicos.
8. **Save persistence:** cracking/fracture durante unload/restart precisa manter state consistente.

## 17. Matriz de testes
- [ ] Backup separado criado antes do primeiro teste destrutivo.
- [ ] Dedicated server inicia com True Impact 0.5.7-delta + Sable 2.0.5.
- [ ] Estrutura parada em contato normal não se autodestrói.
- [ ] Impacto de baixa/alta energia produz resposta diferenciada.
- [ ] Materiais diferentes demonstram resistência coerente sem crash.
- [ ] Cracking acumula sem duplicar state após relog/restart.
- [ ] Fracture não deixa estruturas/blocks fantasmas.
- [ ] Terrain destruction ocorre uma única vez por impacto server-side.
- [ ] Aeronautics 1.3.2 collision path funciona sem double damage.
- [ ] Tracks+ atravessa terreno normalmente sem dano de contato estático.
- [ ] Impacto severo envolvendo Tracks+ não duplica drops.
- [ ] Large-surface collision permanece dentro do orçamento de tick aceitável.
- [ ] Chunk boundary/restart durante dano não corrompe save.
Nenhum teste foi marcado como aprovado nesta auditoria.

## 18. Evidências
- Modlist física canônica 08/09/2026: True Impact 0.5.7-delta, Sable 2.0.5, Create 6.0.10, Aeronautics 1.3.2 e Tracks+ 1.0.6b6.
- CurseForge oficial Sable: True Impact: ambiente, Beta atual e features de impact damage, terrain destruction, material strength, cumulative cracking, early fracture, impact sampling, static-contact protection e config.
- Limitações/recomendação de backup vêm da própria documentação upstream; não são inferências desta auditoria.
