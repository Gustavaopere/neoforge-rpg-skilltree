# Critérios Obrigatórios para Aprovação de Perks — RPG Skill Tree

> **REGRA DE OURO:** uma perk só pode ser declarada **APROVADA/FECHADA** quando passar por **todos** os critérios desta página. Correção mecânica isolada, texto preenchido ou efeito tecnicamente possível **não são suficientes**.

Esta página é o protocolo permanente de auditoria do catálogo de perks do **RPG Skill Tree — NeoForge 1.21.1**. Todo novo chat, agente ou execução que criar, revisar, corrigir ou implementar perks deve ler esta página **antes de trabalhar no catálogo**.

Fonte canônica no Notion: https://app.notion.com/p/3c669db9f0db81e2a0f7cd9b2d410567

## 1. Fontes de verdade obrigatórias

A aprovação deve cruzar, no mínimo:

- o **Documento Mestre de Design e Implementação** do projeto;
- o **Catálogo Mestre — Atributos e Passivos** no Notion;
- [GUIA COMPLETO — Mods de Magia | NeoForge 1.21.1](https://app.notion.com/p/3c569db9f0db819e9572fd43820f9c03);
- [GUIA COMPLETO — Mods de Tecnologia | NeoForge 1.21.1](https://app.notion.com/p/3c569db9f0db81a69e3ee1232ee636ff);
- [GUIA COMPLETO — Gameplay e Sistemas | NeoForge 1.21.1](https://app.notion.com/p/3c569db9f0db81dab0bdd4c8fc783fb6);
- a **modlist atual do modpack**, quando houver versão mais recente que os guias;
- código-fonte, API, documentação ou comportamento verificado da **versão exata do provider** usada no Minecraft **1.21.1 / NeoForge**;
- repositório real do projeto quando a perk já possuir implementação.

Se houver conflito, prevalece a evidência técnica mais específica e atual da versão instalada. Suposições de gameplay nunca substituem API/código/documentação reais.

## 2. Os 9 eixos obrigatórios de aprovação

> Cada perk deve ser examinada explicitamente contra os nove eixos abaixo. Um lote só fecha quando os nove eixos foram conferidos para todas as perks do intervalo.

### 2.1 Dependências, bloqueios e gates

A perk deve possuir bloqueio coerente quando depender de outra progressão.

Obrigatório verificar:

- `Dependências Obrigatórias` corretas;
- rank mínimo da dependência, quando aplicável;
- mastery necessária;
- pontos/investimento de domínio, quando aplicável;
- gateways e condições externas;
- ausência de atalhos que permitam comprar a perk sem percorrer sua progressão real;
- ausência de dependência circular;
- dependência semanticamente coerente, e não apenas um código que existe.

**Regra:** se A depende de B, o runtime deve ser capaz de impedir a aquisição/ativação de A enquanto B não estiver satisfeita.

### 2.2 Integração global entre corpo, sobrevivência, magia, tecnologia e demais sistemas — modlist 2026-08-25

Nenhuma perk deve ser analisada como se o mod provider existisse isoladamente.

Sempre perguntar se a perk interage com sistemas compartilhados do modpack, incluindo quando pertinente:

- temperatura corporal, frio, calor e ambiente;
- Cold Sweat como estado corporal térmico; Create: Cold Sweat apenas como bridge; Ecliptic Seasons como clima/estações sem equivalência automática com temperatura corporal;
- sede/hidratação;
- nutrição;
- vida, sangue e recursos corporais;
- cura, sobrecura, regeneração e ferimentos;
- mana e recursos mágicos reais;
- Soul Energy, spirits e outros recursos occult reais;
- stamina/Epic Fight;
- peso/encumbrance **somente quando houver provider real do jogador**; `Weight 1.2.0` atual é massa física Aeronautics/Sable e não pode preencher esse papel;
- radiação, contaminação ou efeitos ambientais existentes;
- energia elétrica, cinética, combustível e fluidos;
- summons, familiars, servants e ownership;
- agricultura, animais, genética e sobrevivência;
- progressões nativas como Vampirism, Bloodlines, Werewolves e sistemas equivalentes.

**Afinidade e resistência não são sinônimos.** Quando o design exigir, afinidade com um elemento pode reduzir efeitos negativos causados pelo próprio uso/elemento invocado, enquanto resistência ambiental/hostil trata exposição externa. Não fundir conceitos diferentes apenas porque ambos envolvem fogo, frio etc.

**Regra:** sempre usar o recurso e o pipeline canônico existente. Não criar uma segunda temperatura, segunda barra de sangue, segunda mana, segundo sistema de cura ou recurso paralelo sem decisão explícita no Documento Mestre.

### 2.3 Qualidade e identidade — nenhuma perk “sem sal”

A perk precisa justificar sua existência na árvore.

Reprovar ou redesenhar perks que sejam apenas:

- `+5%` ou `+10%` genérico sem decisão de build;
- cópia de atributo que já existe em outro ramo;
- bônus que não muda comportamento, estratégia, especialização ou fantasia;
- duplicação artificial de três nodes que poderiam ser um Ranked Passive;
- Notable/Keystone/Capstone com impacto incompatível com o nome e posição.

Preferir, quando suportado:

- novas decisões;
- tradeoffs;
- telemetria útil;
- planejamento/preflight;
- interação entre mecânicas reais;
- especialização operacional;
- alteração de uma regra nativa por hook estável;
- sinergias que não poderiam ser substituídas por um atributo genérico.

### 2.4 Ramificação, distância e topologia da árvore

A aprovação exige conferir **onde** a perk está na árvore, não apenas o que ela faz.

Obrigatório verificar:

- ramo e domínio corretos;
- camada coerente;
- acesso pela vizinhança correta;
- distância suficiente para builds híbridas;
- ausência de “teleporte” entre regiões distantes;
- Bridges/corredores quando a travessia entre regiões deve custar investimento;
- posição de Gateway, Notable, Keystone e Capstone coerente com o poder;
- custo total do caminho compatível com o benefício.

Travessias longas devem preferir **corredores compráveis de Bridge Nodes**. A referência de design é aproximadamente **8–12 Passive Points** para uma travessia longa, ajustável por balanceamento, e não uma taxa invisível aplicada a um único node.

### 2.5 Especializações

Toda perk relacionada a um provider ou subdisciplina deve ser conferida contra a arquitetura de especializações.

Verificar:

- se pertence à especialização certa;
- se deve ser uma especialização, classe emergente, ramo universal ou ponte;
- se o Gateway aparece no ponto adequado;
- se a perk invade responsabilidades de outra especialização;
- se há progressão I → II → III ou equivalente com identidade crescente, e não apenas números maiores;
- se o Capstone realmente conclui a fantasia da especialização;
- se especializações sem classe obrigatória continuam possíveis quando o design assim prevê.

Mods **não viram classes automaticamente**. O provider fornece mecânicas para domínios e especializações.

### 2.6 Tradução PT-BR

Todo texto destinado ao jogador deve ser apresentado em **português do Brasil**, salvo nomes próprios de mods, itens, mecânicas cujo nome oficial precise ser preservado ou termos sem tradução adequada.

Obrigatório conferir:

- nome da perk;
- descrição/efeito;
- tooltip;
- mensagens de bloqueio;
- requisitos apresentados ao jogador;
- nomes de perfis/modos criados pelo RPG Skill Tree;
- consistência terminológica entre perks.

Nomes de classes Java, métodos, IDs, tags, registries e `Hook` técnico podem permanecer em inglês porque fazem parte da implementação.

### 2.7 Preenchimento completo do Notion

Uma perk não é aprovada enquanto seu registro estiver incompleto ou contraditório.

Conferir e preencher, conforme o schema aplicável:

- `Código`;
- `Nome`;
- `Domínio`;
- `Árvore`;
- `Ramo`;
- `Camada`;
- `Função na Árvore`;
- `Tier`;
- `Faixa de Poder`;
- `Ranks Máx.`;
- `Custo por Rank`;
- `Custo Extra`, quando existir;
- `Dependências Obrigatórias`;
- `Pré-requisitos`;
- `Provider/Mods`;
- `Efeito`;
- `Escalonamento`;
- `Gate`;
- `Hook`;
- `Fallback`;
- `Regra`;
- demais propriedades existentes no catálogo que sejam pertinentes.

O texto deve ser suficientemente específico para implementação posterior sem o próximo desenvolvedor precisar reinventar a intenção da perk.

### 2.8 Remoção total do NeoVitae

**NeoVitae é legado e deve ser removido do projeto.**

Para aprovação:

- a perk não pode exigir NeoVitae;
- `Provider/Mods` não pode manter NeoVitae como provider ativo;
- hooks NeoVitae antigos devem ser migrados ou removidos;
- recursos, afinidades, entidades ou mecânicas que só existiam no NeoVitae não podem ser fingidos como existentes;
- se a fantasia continuar válida, ela deve ser redirecionada a providers reais remanescentes, somente quando semanticamente correto;
- toda revisão deve procurar referências residuais, inclusive em perks antigas.

A presença histórica do NeoVitae em documentos ou modlists antigas **não autoriza** criar novas dependências.

### 2.9 Cobertura completa da modlist e integração entre mods

Os guias de mods não servem apenas como documentação: eles são uma **matriz de cobertura** para o desenho da árvore.

Obrigatório em cada lote:

- revisar os providers pertinentes nos três guias e na modlist atual;
- perguntar quais mecânicas desses mods deveriam alimentar as perks do intervalo;
- verificar bridges entre mods que representam a mesma fantasia/sistema;
- evitar ignorar mods menores ou periféricos apenas porque os grandes providers já possuem perks;
- registrar quando um mod **não deve** ter perk própria e explicar se ele é coberto por um sistema universal;
- procurar lacunas de integração antes de declarar o lote fechado.

**Exemplo de alerta permanente:** `Protection Pixel` foi citado como caso de mod que pode ter sido deixado passar. Portanto, a auditoria não pode presumir que a catalogação anterior cobriu todos os providers. A cobertura deve ser revalidada sistematicamente.

## 3. Critérios técnicos obrigatórios

### 3.1 Provider-native first

A integração deve respeitar primeiro a mecânica nativa do provider.

Se o mod já possui sistema adequado de combustível, energia, durabilidade, prioridade, progressão, cooldown, skillbook, resource cost ou unlock, a perk não deve clonar ou substituir esse sistema sem justificativa explícita.

### 3.2 Não inventar mecânicas do provider

É proibido afirmar que uma perk controla algo que a versão auditada do mod não possui.

Exemplos de erros:

- reduzir “perda de transmissão” em rede que não possui perdas;
- oferecer filtro de minério onde a máquina não possui seleção de alvo;
- criar prioridade automática onde o provider distribui aleatoriamente;
- usar pressão como gate quando o runtime não a aplica;
- criar durabilidade reduzida para equipamento marcado como unbreakable.

### 3.3 Hook implementável e versão exata

O `Hook` deve identificar a fonte real do evento/estado que implementará a perk.

Sempre verificar a versão usada no pack. Código de branch posterior não é evidência suficiente para NeoForge 1.21.1.

### 3.4 Fail-closed

Quando um hook seguro não estiver disponível, a perk fica inativa para aquele provider/caso ou omite a funcionalidade indisponível.

É proibido transformar silenciosamente uma perk quebrada em `+dano`, `+mastery`, `+durabilidade` ou outro bônus genérico para fazê-la “funcionar”.

### 3.5 Fallback preserva identidade

Fallback é degradação segura, não uma segunda perk.

Exemplo correto: uma telemetria deixa de mostrar uma métrica sem adapter.

Exemplo incorreto: um sistema de planejamento vira `+10% produção` se a API não existir.

### 3.6 Uma ação, um pipeline canônico

O mesmo evento não pode ser processado duas vezes por bridges diferentes.

Aplicar especialmente a:

- cura;
- crítico;
- stamina;
- dano;
- mastery;
- summons;
- produção industrial;
- recursos mágicos.

### 3.7 Sem geração ou duplicação acidental

Uma perk não pode produzir gratuitamente:

- energia;
- combustível;
- fluidos;
- minério;
- ingredientes;
- outputs extras não previstos;
- recursos mágicos;
- vida/sangue;
- drops;
- pontos/mastery.

Qualquer geração intencional precisa ser parte explícita do design e ter custo/gate/hook auditado.

### 3.8 Read-only deve ser realmente read-only

Perks de diagnóstico, telemetria, previsão e planejamento podem ler/simular, mas não devem consumir recursos, executar receita, alterar máquina, mover bloco, reconfigurar rede ou produzir resultado durante a consulta.

Quando existir API de simulação, usar a operação de simulação.

## 4. Regras de Mastery e anti-abuso

Mastery representa **experiência real e atribuível**, não tempo conectado.

### 4.1 Proibido gerar Mastery por

- tick;
- tempo AFK;
- tempo com equipamento vestido;
- distância percorrida continuamente;
- RF/t, FE/t, SU/t ou throughput;
- dano repetitivo sem milestone;
- segurar botão continuamente;
- processo autônomo sem autoria demonstrável;
- desmontar e reconstruir a mesma estrutura;
- alternar repetidamente a mesma configuração;
- farm infinito de evento idêntico.

### 4.2 Preferir milestones discretos

Exemplos:

- primeira utilização legítima de uma categoria;
- nova família de ferramenta/máquina;
- nova receita relevante;
- nova configuração real;
- primeiro comissionamento de arquitetura distinta;
- conquista/advancement;
- descoberta inédita registrada em ledger limitado;
- milestone funcional claramente atribuível.

### 4.3 Autoria causal

Automação, fake player, redstone, turret, minion ou máquina abandonada não concede automaticamente Mastery ao jogador.

Só recompensar quando houver regra estável de ownership/autoria e o resultado puder ser atribuído inequivocamente.

## 5. Coerência de poder e função na árvore

### 5.1 Small / Ranked Passive

Bônus simples são aceitáveis quando pequenos, canônicos e necessários para formar caminho. Cadeias redundantes devem virar Ranked Passive quando possível.

### 5.2 Notable

Deve alterar decisão de build, interação ou fantasia. Não pode ser um pequeno percentual com nome grandioso.

### 5.3 Gateway

Serve para desbloquear sistema, especialização ou camada. Normalmente não deve carregar um grande pacote de poder próprio.

### 5.4 Keystone

Altera regra relevante de gameplay e pode envolver tradeoff. Exige hook seguro e documentação precisa.

### 5.5 Capstone

Deve representar conclusão significativa do ramo/especialização. Não basta ser “o mesmo bônus do rank anterior, porém maior”.

### 5.6 Bridge

Conecta regiões e deve justificar o caminho entre as duas fantasias. Bridge não deve ser falso Notable nem teleporte gratuito.

## 6. Checklist individual de aprovação

Uma perk somente recebe **APROVADA** se todas as respostas abaixo forem `SIM` ou `N/A justificado`:

- [ ] Código e identidade estão corretos?
- [ ] Dependências e gates bloqueiam corretamente a aquisição/ativação?
- [ ] A perk está no domínio, ramo, camada e posição corretos?
- [ ] A distância/topologia da árvore está coerente?
- [ ] A função estrutural está correta — Small, Ranked, Bridge, Gateway, Notable, Keystone ou Capstone?
- [ ] A faixa de poder, ranks e custos correspondem ao impacto?
- [ ] A perk é interessante e possui identidade própria?
- [ ] Não existe uma perk anterior que já faça essencialmente a mesma coisa?
- [ ] A especialização está correta e não invade outra?
- [ ] Todas as integrações globais pertinentes foram consideradas?
- [ ] Os providers pertinentes da modlist/guias foram consultados?
- [ ] O comportamento existe de verdade na versão do provider instalada?
- [ ] O hook é implementável e específico?
- [ ] O efeito respeita o provider-native first?
- [ ] Não inventa mecânica, recurso, atributo ou estado inexistente?
- [ ] O fallback é seguro e preserva a identidade?
- [ ] Não duplica pipelines ou recompensa a mesma ação duas vezes?
- [ ] Não cria exploit de energia, recursos, outputs, cura, sangue, mana ou mastery?
- [ ] Mastery, se existir, usa milestone discreto e atribuição causal?
- [ ] Possui proteção anti-farm/rebuild quando necessária?
- [ ] Não possui dependência residual de NeoVitae?
- [ ] O texto visível ao jogador está em PT-BR consistente?
- [ ] Todos os campos pertinentes do Notion estão preenchidos?
- [ ] Após a escrita, a página foi buscada novamente e a persistência foi confirmada?

## 7. Status permitidos

Use apenas estes estados conceituais durante a auditoria:

**PENDENTE** — ainda não foi auditada pelos critérios completos.

**EM REVISÃO** — análise em andamento; não pode ser tratada como pronta.

**BLOQUEADA** — depende de informação/API/provider ainda não confirmado.

**REPROVADA / REDESENHAR** — falhou em um ou mais critérios e requer mudança funcional/estrutural.

**APROVADA** — passou individualmente por todos os critérios aplicáveis e foi re-fetched no Notion.

**LOTE FECHADO** — todas as perks do intervalo estão aprovadas e a matriz de cobertura do lote também foi concluída.

## 8. Protocolo obrigatório de auditoria por lotes

Para revisão de catálogo antigo, trabalhar preferencialmente em **lotes de 50 perks** para preservar precisão.

Ao iniciar um lote, registrar explicitamente:

- `INÍCIO: Axxxx`;
- `FIM: Ayyyy`;
- estado inicial do intervalo;
- fontes/mods/providers que serão cruzados.

Durante o lote, manter esta matriz:

| Critério | Status do lote |
|---|---|
| 1. Dependências e bloqueios | ⬜ |
| 2. Integrações globais/modlist atual/corpo/recursos | ⬜ |
| 3. Qualidade/identidade | ⬜ |
| 4. Ramificação/distância/topologia | ⬜ |
| 5. Especializações | ⬜ |
| 6. PT-BR | ⬜ |
| 7. Notion completo | ⬜ |
| 8. NeoVitae removido | ⬜ |
| 9. Cobertura da modlist/providers | ⬜ |

Um `✅` só pode ser colocado depois da verificação real de todas as perks pertinentes naquele critério.

### 8.1 Fechamento do lote

Antes de anunciar `Axxxx–Ayyyy — FECHADO`:

1. terminar todas as correções;
2. re-fetch das páginas alteradas no Notion;
3. confirmar a persistência;
4. confirmar os nove eixos da matriz;
5. registrar exceções/N/A com justificativa;
6. informar claramente ao usuário o intervalo efetivamente fechado.

Se a execução parar no meio, informar o **último código realmente aprovado** e o **próximo código exato**. Nunca arredondar o progresso.

## 9. Auditoria de cobertura de mods

A revisão de perks deve manter uma segunda pergunta paralela: **“há alguma mecânica do modpack que ainda não está representada ou integrada?”**

Para cada provider/mod relevante, classificar sua cobertura como uma destas opções:

- **PERK PRÓPRIA** — precisa de nodes dedicados;
- **ESPECIALIZAÇÃO** — pertence a uma progressão temática própria;
- **BRIDGE** — conecta dois sistemas/domínios existentes;
- **COBERTO POR SISTEMA UNIVERSAL** — não precisa de perk nominal do mod;
- **PROGRESSÃO NATIVA AUTORITATIVA** — o sistema do próprio mod deve permanecer principal, com integração mínima;
- **SEM HOOK SEGURO** — integração adiada/fail-closed;
- **NÃO DEVE SER INTEGRADO** — justificar explicitamente.

Isso impede tanto esquecer providers quanto criar uma árvore com uma especialização desnecessária para cada mod instalado.

## 10. Regra para novos chats/agentes

> **Antes de continuar qualquer lote:** leia esta página, identifique o intervalo exato, consulte as fontes de verdade e use a checklist completa. Não aceite como definitivamente fechado um intervalo antigo apenas porque outro chat escreveu `FECHADO` se ele ainda não tiver passado por esta versão completa da auditoria.

Ao retomar trabalho já iniciado:

- preservar decisões tecnicamente confirmadas;
- não refazer trabalho sem motivo;
- porém reabrir uma perk se nova evidência revelar violação destes critérios;
- documentar a razão da reabertura;
- continuar do ponto exato informado pelo último estado verificado.

## 11. Política de aprovação final

Uma perk é considerada pronta para implementação somente quando satisfaz simultaneamente:

**Design coerente + topologia coerente + dependências corretas + integração global + cobertura de providers + API/hook real + anti-abuso + PT-BR + Notion completo + ausência de NeoVitae + persistência confirmada.**

Se qualquer uma dessas partes estiver ausente, o status correto é **PENDENTE**, **BLOQUEADA** ou **REDESENHAR**, nunca `APROVADA`.

## 12. Checklist técnica consolidada — 18 critérios obrigatórios

> Esta seção consolida, de forma explícita, os critérios técnicos mínimos que **toda perk** deve cumprir além dos 9 eixos sistêmicos acima. Se qualquer item falhar, a perk não pode ser declarada aprovada/fechada.

1. **O efeito precisa existir de verdade.** Quando depender de um mod/provider, validar API, código, documentação ou comportamento da versão real instalada. Não aceitar descrições abstratas como “melhora eficiência” sem parâmetro ou hook real correspondente.
2. **Provider-native first.** Preservar primeiro a mecânica nativa do mod. A skill tree não deve substituir, duplicar ou contornar sistemas que o provider já oferece adequadamente.
3. **Sem mecânica inventada disfarçada de integração.** Não criar prioridade de rede, perda de energia, filtro de minério, estabilização, pressão funcional, durabilidade ou qualquer outra mecânica que a versão auditada do provider não possua.
4. **Fail-closed.** Sem hook/API segura, a perk fica inativa naquele caso ou omite a funcionalidade. Nunca substituir silenciosamente por `+mastery`, `+durabilidade`, `+dano` ou bônus genérico.
5. **Fallback não pode mudar a identidade da perk.** Fallback serve para degradação técnica segura; não pode transformar uma perk de planejamento/controle/telemetria em outro bônus completamente diferente.
6. **Mastery somente por feitos discretos e atribuíveis.** Nunca por tick, RF/t, FE/t, SU/t, stress/t, tempo AFK, throughput contínuo, dano repetido, equipamento vestido, distância contínua ou reconstrução repetitiva. Preferir ledger persistente para first-use, nova família, nova receita, nova configuração e milestones reais.
7. **Anti-farm e anti-rebuild.** Desmontar/remontar a mesma estrutura, mover a máquina, alternar a mesma configuração ou repetir o mesmo resultado não pode gerar nova descoberta/mastery indefinidamente.
8. **Atribuição causal ao jogador.** Máquina autônoma, redstone, fake player, turret ou processo abandonado só pode gerar mastery quando houver autoria/atribuição inequívoca ao jogador segundo a política do sistema.
9. **Não duplicar pipelines canônicos.** Crítico, cura, stamina, dano, mastery, summons e recursos devem usar o resolvedor/evento canônico. É proibida segunda rolagem ou processamento duplicado do mesmo evento.
10. **Custos e recursos têm que ser reais.** RF/FE, Soul Energy, sangue, mana, stamina, combustível, fluidos, durabilidade e outros custos devem ler/modificar o recurso verdadeiro do provider. Não criar barra paralela ou custo fictício sem decisão explícita de design.
11. **Sem geração gratuita ou duplicação acidental.** Não duplicar outputs, devolver ingrediente já consumido, criar energia/combustível/minério/resource node/fluidos/vida/sangue/mana/mastery ou converter telemetria em alteração de processo sem design explícito, custo e hook auditados.
12. **Read-only significa realmente read-only.** Diagnóstico, telemetria, planejamento e previsão podem ler/calcular/simular, mas não consumir recursos, executar receita, produzir item, alterar configuração, mover bloco ou mudar estado durante a consulta.
13. **Versionamento explícito quando a API for sensível à versão.** Registrar e auditar a versão efetivamente usada no pack, por exemplo `Oritech 1.2.11 — NeoForge 1.21.1`. Código de versão/branch posterior não vale como prova automática.
14. **Coerência estrutural da árvore.** Conferir função, camada, ranks, custo, faixa de poder, convergência de dependências e posição. Notable não pode ser bônus banal; Keystone deve alterar regra relevante; Capstone precisa ser digno de fim de ramo; Gateway/Ponte precisam cumprir função topológica real.
15. **Dependências precisam ter semântica correta.** Não basta o código existir. A dependência deve representar progressão mecanicamente e tematicamente coerente, usando o provider/recurso correto.
16. **Sem sobreposição indevida entre ramos/especializações.** Cada ação e mecânica deve alimentar os domínios/especializações que realmente lhe pertencem. Usar energia, produzir item ou possuir múltiplas capacidades não autoriza a mesma atividade a conceder mastery em vários ramos sem justificativa explícita. Bridges devem ser intencionais e anti-double-count.
17. **A perk precisa ser implementável posteriormente.** `Hook`, `Gate`, `Efeito`, `Escalonamento`, requisitos e regras devem ser específicos o bastante para outro ChatGPT/Codex implementar sem reinterpretar ou reinventar o design.
18. **Verificação pós-escrita obrigatória.** Depois de criar ou alterar uma perk no Notion, buscar/fazer fetch novamente e confirmar que as propriedades persistiram corretamente. Só então o node/lote pode ser chamado de `FECHADO`.

### 12.1 Campos mínimos do registro da perk

Além dos 18 critérios, conferir a coerência dos campos do catálogo aplicáveis: `Código`, `Nome`, `Domínio`, `Árvore`, `Ramo`, `Camada`, `Função na Árvore`, `Tier`, `Faixa de Poder`, `Ranks Máx.`, `Custo por Rank`, `Custo Extra`, `Dependências Obrigatórias`, `Pré-requisitos`, `Provider/Mods`, `Efeito`, `Escalonamento`, `Gate`, `Hook`, `Fallback`, `Regra` e quaisquer outras propriedades pertinentes do schema atual.

**Regra final:** os 18 critérios desta seção são cumulativos aos 9 eixos obrigatórios da Seção 2. Nenhum deles substitui integração global, topologia, especializações, PT-BR, remoção do NeoVitae, cobertura da modlist ou preenchimento completo do Notion.

---

## Nota de sincronização

Este arquivo é uma cópia versionada do conteúdo canônico do Notion consultado em 2026-08-29. **O Notion continua sendo a fonte canônica**: se a página de critérios mudar, este arquivo deve ser atualizado antes do próximo lote de perks.