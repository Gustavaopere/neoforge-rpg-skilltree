# 07 — Checklist Canônico de Implementação das Perks

Este arquivo registra somente perks que já passaram pela auditoria de design exigida por **Critérios Obrigatórios para Aprovação de Perks — RPG Skill Tree**.

- `[ ]` = especificação aprovada no Notion, ainda não confirmada como implementada e mergeada em `main`.
- `[x]` = implementação da perk confirmada em `main` por outro trabalho de implementação.
- A caixa não deve ser marcada apenas porque existe código parcial, branch, commit ou PR aberta.
- A especificação abaixo reproduz os campos editáveis do **Catálogo Mestre — Atributos e Passivos**. Campos `* Efetivo` são fórmulas derivadas do Notion e não são duplicados aqui.

Fonte canônica: `Catálogo Mestre — Atributos e Passivos` — NeoForge 1.21.1.

---

- [ ] **A0001 — Treino com Espadas I**

  - **Código:** A0001
  - **Nome:** Treino com Espadas I
  - **Domínio:** MARTIAL
  - **Árvore:** Epic Fight — Espadas
  - **Ramo:** Ritmo e Velocidade
  - **Camada:** 1
  - **Função na Árvore:** Ramo
  - **Tier:** Pequeno
  - **Faixa de Poder:** Baixo
  - **Ranks Máx.:** 3
  - **Custo por Rank:** 1
  - **Dependências Obrigatórias:** 
  - **Pré-requisitos:** Gateway de disciplina de Espadas (`epic_sword`).
  - **Provider/Mods:** Epic Fight 21.17.3.1 + Minecraft/NeoForge + RPG Skill Tree
  - **Efeito:** +3% de dano com espadas por rank (máx. +9%).
  - **Escalonamento:** Até 3 ranks; +3% de dano com espadas por rank.
  - **Gate:** Nível 8 + maestria de espadas (`epicfight:sword`) ≥ 60 + Gateway de disciplina de Espadas (`epic_sword`) desbloqueado. Esse gateway pertence à Árvore Exterior e não é uma Árvore de Especialista.
  - **Hook:** Categoria de arma espada + evento normalizado de dano corpo a corpo do RPG Skill Tree.
  - **Fallback:** Tag configurável `rpgskilltree:swords` quando a categoria de arma do provider não estiver disponível; nunca duplicar bônus se Epic Fight já classificou a arma.
  - **Regra:** Bônus específico maior que dano universal. FUNDAMENTO_EXTERIOR: ESPADAS. Pode compor SPECIALIST_FUNDAMENTALS de um especialista cuja identidade exija espada, mas não desbloqueia especialista sozinho; proximidade visual e border hopping nunca substituem os gates semânticos.
  - **Fonte Notion:** https://app.notion.com/p/3c569db9f0db8165adfcc38d24e537f1

---

- [ ] **A0002 — Treino com Espadas II**

  - **Código:** A0002
  - **Nome:** Treino com Espadas II
  - **Domínio:** MARTIAL
  - **Árvore:** Epic Fight — Espadas
  - **Ramo:** Ritmo e Velocidade
  - **Camada:** 2
  - **Função na Árvore:** Ramo
  - **Tier:** Pequeno
  - **Faixa de Poder:** Baixo
  - **Ranks Máx.:** 3
  - **Custo por Rank:** 1
  - **Dependências Obrigatórias:** A0001 Treino com Espadas I ≥ 2 ranks.
  - **Pré-requisitos:** A0001
  - **Provider/Mods:** Epic Fight 21.17.3.1 + Minecraft/NeoForge
  - **Efeito:** +2% de velocidade/ritmo efetivo com espadas por rank (máx. +6%), respeitando limites do moveset/provider.
  - **Escalonamento:** Até 3 ranks; +2% de velocidade/ritmo efetivo com espadas por rank, até +6%.
  - **Gate:** Gateway de disciplina de Espadas (`epic_sword`) acessível + A0001 Treino com Espadas I ≥ 2 ranks. O gateway é da Árvore Exterior, não uma Árvore de Especialista.
  - **Hook:** Epic Fight 21.17.3.1: categoria de espada + modificador de attack speed compatível com `EpicFightAttributes.ATTACK_SPEED_MODIFIER`/`getSpeedBonusModifier`, somente quando o moveset realmente usar esse valor de forma server-authoritative.
  - **Fallback:** Se o provider não expuser um modificador server-authoritative e estável de cadência/ritmo para espadas, esta parcela de ritmo fica inativa. Não converter para estamina, movimento, dano ou outro bônus e não acelerar animações por mixin, heurística ou modificação frágil.
  - **Regra:** Usar somente se o provider expuser um atributo/estado estável correspondente ao próprio efeito. Fallback não pode mudar a identidade da perk.
  - **Fonte Notion:** https://app.notion.com/p/3c569db9f0db8113af7ef99d93dfe751

---

- [ ] **A0003 — Precisão com Espadas**

  - **Código:** A0003
  - **Nome:** Precisão com Espadas
  - **Domínio:** MARTIAL
  - **Árvore:** Epic Fight — Espadas
  - **Ramo:** Crítico e Precisão
  - **Camada:** 2
  - **Função na Árvore:** Ramo
  - **Tier:** Pequeno
  - **Faixa de Poder:** Médio
  - **Ranks Máx.:** 3
  - **Custo por Rank:** 1
  - **Dependências Obrigatórias:** A0001 Treino com Espadas I ≥ 1 rank.
  - **Pré-requisitos:** A0001 Treino com Espadas I.
  - **Provider/Mods:** Epic Fight 21.17.3.1 + RPG Skill Tree — pipeline crítico canônico
  - **Efeito:** +3% de chance de crítico com espadas por rank (máx. +9%).
  - **Escalonamento:** Até 3 ranks.
  - **Gate:** Gateway de disciplina de Espadas (`epic_sword`) acessível + A0001 Treino com Espadas I ≥ 1 rank. O gateway pertence à Árvore Exterior, não à Árvore de Especialista.
  - **Hook:** Classificação de espada + pipeline canônico de chance de crítico.
  - **Fallback:** Usar chance de crítico canônica somente em ataques diretos de espada; ignorar fontes não classificadas.
  - **Regra:** Chance de crítico específica para espadas; não cria segunda rolagem quando o provider ou o RPG Skill Tree já resolveu o crítico. Uma ação elegível produz no máximo uma resolução crítica canônica.
  - **Fonte Notion:** https://app.notion.com/p/3c569db9f0db816bab5bcc64e7081fe7

---

- [ ] **A0004 — Ritmo do Duelista**

  - **Código:** A0004
  - **Nome:** Ritmo do Duelista
  - **Domínio:** MARTIAL
  - **Árvore:** Epic Fight — Espadas
  - **Ramo:** Duelista — Ímpeto
  - **Camada:** 3
  - **Função na Árvore:** Notable
  - **Tier:** Médio
  - **Faixa de Poder:** Médio
  - **Ranks Máx.:** 1
  - **Custo por Rank:** 1
  - **Dependências Obrigatórias:** A0003 Precisão com Espadas ≥ 2 ranks. Uma rota lateral não substitui esta dependência.
  - **Pré-requisitos:** A0003
  - **Provider/Mods:** Epic Fight 21.17.3.1 + RPG Skill Tree
  - **Efeito:** Acertos diretos limpos com espada geram 1 de Ímpeto, até 5 cargas. Aparo, riposta ou esquiva que realmente evitou uma ameaça podem gerar 1 carga em seu próprio evento quando o provider expuser confirmação segura. Um ataque de espada iniciado pelo jogador que termine sem acertar alvo elegível remove 1 carga, uma ocorrência de desequilíbrio/stagger pesado causada por fonte hostil remove 2 cargas e, após 5 s sem acerto válido de espada nem defesa técnica elegível, Ímpeto perde 1 carga por segundo até chegar a 0.
  - **Escalonamento:** 1 rank. Ímpeto é estado transitório canônico de MARTIAL com limite 5; A0004 habilita sua geração pela disciplina de espada. Cada ganho elegível reinicia o temporizador de inatividade de 5 s; perdas não reiniciam esse temporizador.
  - **Gate:** Gateway de disciplina de Espadas (`epic_sword`) acessível + A0003 Precisão com Espadas ≥ 2 ranks. O gateway pertence à Árvore Exterior, não à Árvore de Especialista.
  - **Hook:** Resultado server-authoritative de acerto direto com espada + término confirmado de ataque sem acerto elegível + eventos confirmados de aparo/riposta/esquiva quando disponíveis + stagger/impacto pesado hostil + relógio server-side do último ganho elegível de Ímpeto.
  - **Fallback:** Sem hook seguro de aparo/esquiva, apenas acertos diretos limpos com espada geram Ímpeto. Se o provider não permitir distinguir com segurança um ataque iniciado que errou, omitir somente a perda por erro; nunca inferir esquiva por distância nem premiar bloqueio passivo.
  - **Regra:** Ímpeto recompensa execução limpa. Um mesmo resultado não pode conceder duas cargas por adapters sobrepostos. Dano autoinfligido, alvo de treino, AFK e callbacks duplicados não contam. Ímpeto é limpo em morte, logout e troca de dimensão; trocar de arma não gera nem renova o estado e ele decai pelo temporizador normal.
  - **Fonte Notion:** https://app.notion.com/p/3c569db9f0db81aeb549d2500a67c0f4

---

- [ ] **A0005 — Abertura de Guarda**

  - **Código:** A0005
  - **Nome:** Abertura de Guarda
  - **Domínio:** MARTIAL
  - **Árvore:** Epic Fight — Espadas
  - **Ramo:** Duelista — Ímpeto
  - **Camada:** 3
  - **Função na Árvore:** Notable
  - **Tier:** Médio
  - **Faixa de Poder:** Médio
  - **Ranks Máx.:** 1
  - **Custo por Rank:** 1
  - **Dependências Obrigatórias:** A0002 Treino com Espadas II ≥ 2 ranks + A0004 Ritmo do Duelista. Uma rota lateral não substitui estas dependências.
  - **Pré-requisitos:** A0002 + A0004
  - **Provider/Mods:** Epic Fight 21.17.3.1 + RPG Skill Tree
  - **Efeito:** Com pelo menos 3 de Ímpeto, um acerto direto de espada contra o mesmo alvo após sequência limpa pode consumir 2 de Ímpeto para criar Abertura: o golpe recebe +12% de penetração física elegível e +8% de impacto/pressão de guarda. Recarga de 6 s por alvo.
  - **Escalonamento:** 1 rank. O benefício é condicional e consome recurso, não bônus permanente.
  - **Gate:** Gateway de disciplina de Espadas (`epic_sword`) acessível + A0002 Treino com Espadas II ≥ 2 ranks + A0004 Ritmo do Duelista adquirido. O gateway pertence à Árvore Exterior.
  - **Hook:** Acerto direto confirmado com espada + registro canônico de Ímpeto + estado defensivo do alvo; quando disponíveis, usar `IMPACT`/`ARMOR_NEGATION` do Epic Fight para os componentes correspondentes, somente no golpe consumidor.
  - **Fallback:** Se guarda/postura nativa não estiver exposta, aplicar somente a penetração física canônica com cap; nunca simular quebra de guarda inexistente.
  - **Regra:** Não ativa em dano periódico ou proc, não encadeia em si mesmo e não ignora os limites globais de penetração. O Gate deve reproduzir integralmente as dependências obrigatórias.
  - **Fonte Notion:** https://app.notion.com/p/3c569db9f0db816cb407cc16ebe41066

---

- [ ] **A0006 — Maestria de Espadas — Riposta Perfeita**

  - **Código:** A0006
  - **Nome:** Maestria de Espadas — Riposta Perfeita
  - **Domínio:** MARTIAL
  - **Árvore:** Epic Fight — Espadas
  - **Ramo:** Duelista — Ímpeto
  - **Camada:** 4
  - **Função na Árvore:** Capstone
  - **Tier:** Grande
  - **Faixa de Poder:** Alto
  - **Ranks Máx.:** 1
  - **Custo por Rank:** 2
  - **Dependências Obrigatórias:** A0004 Ritmo do Duelista + A0005 Abertura de Guarda + maestria de espadas (`epicfight:sword`) ≥ 80. A chegada por ponte/rota alternativa não substitui esses requisitos.
  - **Pré-requisitos:** A0004 + A0005
  - **Provider/Mods:** Epic Fight 21.17.3.1 + RPG Skill Tree — Ímpeto e pipeline crítico canônico
  - **Efeito:** Ao atingir 5 de Ímpeto, uma defesa técnica confirmada — aparo, guarda perfeita ou esquiva que realmente evitou um ataque elegível — prepara Riposta Perfeita por 3 s. O próximo acerto direto de espada consome todo o Ímpeto, recebe +20% de dano crítico elegível e +20% de impacto/pressão de guarda, e não pode gerar Ímpeto no mesmo resultado. Recarga de 10 s.
  - **Escalonamento:** 1 rank. Maestria ≥ 90 reduz a recarga para 9 s; ≥ 100 para 8 s. Não aumenta coeficientes.
  - **Gate:** Gateway de disciplina de Espadas (`epic_sword`) acessível + A0004 Ritmo do Duelista + A0005 Abertura de Guarda + maestria de espadas (`epicfight:sword`) ≥ 80. O gateway pertence à Árvore Exterior.
  - **Hook:** Registro de Ímpeto + evento confirmado de defesa técnica + próximo acerto direto de espada; profundidade de proc e deduplicação obrigatórias.
  - **Fallback:** Se o provider não expuser nenhum evento confiável de aparo, guarda perfeita ou esquiva, o capstone fica indisponível naquele provider em vez de fabricar uma defesa por heurística.
  - **Regra:** Capstone de execução técnica: exige exatamente A0004/A0005 e maestria correspondente; não ativa por bloqueio passivo, invulnerabilidade, dano autoinfligido ou spam de esquiva sem ameaça real. TERMINAL_EXTERIOR: MARTIAL/ESPADAS. Este terminal satisfaz somente o Gate C de especialistas que o mapearem explicitamente; fundamentos e ≥100 Passive Points no ramo/região semântica continuam obrigatórios. RESPEC: enquanto houver qualquer perk de Especialista dependente, bloquear refund deste terminal, dos fundamentos exigidos e de qualquer refund que reduza o investimento válido abaixo do mínimo; o jogador deve devolver primeiro as perks da Árvore de Especialista.
  - **Fonte Notion:** https://app.notion.com/p/3c569db9f0db81aeaae1db665043dc71

---

- [ ] **A0007 — Treino com Machados I**

  - **Código:** A0007
  - **Nome:** Treino com Machados I
  - **Domínio:** MARTIAL
  - **Árvore:** Epic Fight — Machados
  - **Ramo:** Varredura e Pressão
  - **Camada:** 1
  - **Função na Árvore:** Ramo
  - **Tier:** Pequeno
  - **Faixa de Poder:** Baixo
  - **Ranks Máx.:** 3
  - **Custo por Rank:** 1
  - **Dependências Obrigatórias:** 
  - **Pré-requisitos:** Gateway de disciplina de Machados (`epic_axe`).
  - **Provider/Mods:** Epic Fight 21.17.3.1 + Minecraft/NeoForge + RPG Skill Tree
  - **Efeito:** +3% de dano com machados por rank (máx. +9%).
  - **Escalonamento:** Até 3 ranks; +3% de dano com machados por rank, até +9%.
  - **Gate:** Nível 8 + maestria de machados (`epicfight:axe`) ≥ 60 + Gateway de disciplina de Machados (`epic_axe`) desbloqueado. Esse gateway pertence à Árvore Exterior e não é uma Árvore de Especialista.
  - **Hook:** Categoria de arma machado + dano corpo a corpo direto normalizado.
  - **Fallback:** Tag configurável `rpgskilltree:axes`; aplicar uma única vez por golpe elegível e nunca duplicar a classificação do Epic Fight.
  - **Regra:** Bônus específico maior que dano universal. FUNDAMENTO_EXTERIOR: MACHADOS. Pode compor SPECIALIST_FUNDAMENTALS de um especialista cuja identidade exija machados, mas não desbloqueia especialista sozinho; proximidade visual e border hopping não substituem os gates semânticos.
  - **Fonte Notion:** https://app.notion.com/p/3c569db9f0db81db9d9fe826285f88b3

---

- [ ] **A0008 — Treino com Machados II**

  - **Código:** A0008
  - **Nome:** Treino com Machados II
  - **Domínio:** MARTIAL
  - **Árvore:** Epic Fight — Machados
  - **Ramo:** Varredura e Pressão
  - **Camada:** 2
  - **Função na Árvore:** Ramo
  - **Tier:** Pequeno
  - **Faixa de Poder:** Baixo
  - **Ranks Máx.:** 3
  - **Custo por Rank:** 1
  - **Dependências Obrigatórias:** A0007 Treino com Machados I ≥ 2 ranks.
  - **Pré-requisitos:** A0007
  - **Provider/Mods:** Epic Fight 21.17.3.1 + Minecraft/NeoForge
  - **Efeito:** +2% de velocidade/ritmo efetivo com machados por rank (máx. +6%), respeitando o moveset/provider.
  - **Escalonamento:** Até 3 ranks; +2% de velocidade/ritmo efetivo com machados por rank, até +6%.
  - **Gate:** Gateway de disciplina de Machados (`epic_axe`) acessível + A0007 Treino com Machados I ≥ 2 ranks. O gateway pertence à Árvore Exterior, não à Árvore de Especialista.
  - **Hook:** Epic Fight 21.17.3.1: categoria de machado + modificador de attack speed compatível com `EpicFightAttributes.ATTACK_SPEED_MODIFIER`/`getSpeedBonusModifier`, somente quando o moveset realmente usar esse valor de forma server-authoritative.
  - **Fallback:** Se o provider não expuser um modificador server-authoritative e estável de cadência/ritmo para machados, esta parcela de ritmo fica inativa. Não converter para estamina, movimento, dano ou outro bônus e não acelerar animações por mixin, heurística ou modificação frágil.
  - **Regra:** Usar somente se o provider expuser um atributo/estado estável correspondente ao próprio efeito. Fallback não pode mudar a identidade da perk.
  - **Fonte Notion:** https://app.notion.com/p/3c569db9f0db8176ade1e7bfe92eb2d5

---

- [ ] **A0009 — Precisão com Machados**

  - **Código:** A0009
  - **Nome:** Precisão com Machados
  - **Domínio:** MARTIAL
  - **Árvore:** Epic Fight — Machados
  - **Ramo:** Fúria e Pressão
  - **Camada:** 2
  - **Função na Árvore:** Ramo
  - **Tier:** Pequeno
  - **Faixa de Poder:** Médio
  - **Ranks Máx.:** 3
  - **Custo por Rank:** 1
  - **Dependências Obrigatórias:** A0007 Treino com Machados I ≥ 1 rank.
  - **Pré-requisitos:** A0007 Treino com Machados I.
  - **Provider/Mods:** Epic Fight 21.17.3.1 + RPG Skill Tree — pipeline crítico canônico
  - **Efeito:** +3% de chance de crítico com machados por rank (máx. +9%).
  - **Escalonamento:** Até 3 ranks.
  - **Gate:** Gateway de disciplina de Machados (`epic_axe`) acessível + A0007 Treino com Machados I ≥ 1 rank. O gateway pertence à Árvore Exterior, não à Árvore de Especialista.
  - **Hook:** Chance de crítico em ataques diretos com categoria de arma machado.
  - **Fallback:** Usar o pipeline crítico canônico apenas em ataques diretos classificados como machado.
  - **Regra:** Chance de crítico específica para machados; não cria segunda rolagem quando o provider ou o RPG Skill Tree já resolveu o crítico. Uma ação elegível produz no máximo uma resolução crítica canônica.
  - **Fonte Notion:** https://app.notion.com/p/3c569db9f0db813384c2f55b4c22b533

---

- [ ] **A0010 — Pressão do Carrasco**

  - **Código:** A0010
  - **Nome:** Pressão do Carrasco
  - **Domínio:** MARTIAL
  - **Árvore:** Epic Fight — Machados
  - **Ramo:** Fúria e Pressão
  - **Camada:** 3
  - **Função na Árvore:** Notable
  - **Tier:** Médio
  - **Faixa de Poder:** Médio
  - **Ranks Máx.:** 2
  - **Custo por Rank:** 1
  - **Dependências Obrigatórias:** A0009 Precisão com Machados ≥ 2 ranks.
  - **Pré-requisitos:** A0009
  - **Provider/Mods:** Epic Fight 21.17.3.1 + RPG Skill Tree — serviço canônico de Fúria
  - **Efeito:** Cada acerto corpo a corpo direto e válido com machado contra inimigo hostil gera 8 de Fúria como ganho-base. O multiplicador do rank é aplicado primeiro; se o alvo for diferente do último alvo hostil legitimamente atingido pelo jogador, o resultado recebe depois ×1,5. Dano autoinfligido, alvo passivo/de treino, entidade invulnerável, tentativa sem dano confirmado, proc secundário e ação sem autoria real não geram Fúria. Fúria é limitada a 100.
  - **Escalonamento:** 2 ranks. Rank 1: ganho normal 8,8 de Fúria por golpe válido e 13,2 ao trocar legitimamente de alvo. Rank 2: 9,6 normal e 14,4 na troca de alvo. O bônus de troca permanece ×1,5 e não escala além disso.
  - **Gate:** Gateway de disciplina de Machados (`epic_axe`) acessível + A0009 Precisão com Machados ≥ 2 ranks. O gateway pertence à Árvore Exterior.
  - **Hook:** Resultado server-authoritative de dano corpo a corpo direto com machado + autoria real do jogador + registro do último alvo hostil legitimamente atingido + serviço canônico de Fúria.
  - **Fallback:** Sem evento específico do Epic Fight, usar apenas dano corpo a corpo direto confirmado com item inequivocamente classificado como machado. Se a autoria/classificação não for segura, não gerar Fúria; nunca conceder por tentativa de ataque.
  - **Regra:** Uma única concessão de Fúria por resultado ofensivo elegível. Ordem canônica: ganho-base 8 → multiplicador de rank → multiplicador de troca de alvo → clamp em 100. Procs, callbacks duplicados, fake players e alvos de treino não contam.
  - **Fonte Notion:** https://app.notion.com/p/3c569db9f0db81fd9b1bcb501b7745ba
