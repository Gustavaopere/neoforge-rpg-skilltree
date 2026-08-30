# Auditoria Obrigatória — A0001–A0020

Data local da revisão: **2026-08-29 (America/Sao_Paulo)**.

## Escopo e regra de decisão

Esta auditoria reabre A0001–A0020 e aplica integralmente `CRITERIOS-OBRIGATORIOS-PARA-APROVACAO-DE-PERKS.md`. O Notion continua sendo a fonte canônica de design. Quando a auditoria encontrou contrato impossível, fallback fictício ou integração sem receipt seguro, o design canônico foi corrigido antes do código. Nenhuma perk é considerada aprovada por proximidade visual, heurística ou comportamento presumido.

Fontes usadas:

- Catálogo Mestre — Atributos e Passivos no Notion;
- Critérios Obrigatórios para Aprovação de Perks — RPG Skill Tree;
- guias reconciliados com a modlist de 28.08.2026;
- Epic Fight 21.17.3.1 / Epic-API correspondente, incluindo `DELIVER_DAMAGE_PRE`, `ON_DODGE`, `ON_STUNNED`, `StunnedEvent`, `StunType`, `IMPACT`, `ARMOR_NEGATION`, alcance e `epicfight:stamina_regen`;
- Cold Sweat 2.4.2, API pública `Temperature` / `Temperature.Trait.CORE`;
- código e testes do RPG Skill Tree.

## Matriz dos nove eixos

Legenda: `PASS` = atende; `PASS/FALLBACK` = atende porque o próprio contrato canônico define fail-closed ou omissão segura daquele componente.

| Código | 1 Gates | 2 Integração global | 3 Identidade | 4 Topologia | 5 Especializações | 6 PT-BR | 7 Notion | 8 NeoVitae | 9 Modlist/integrações | Resultado |
|---|---|---|---|---|---|---|---|---|---|---|
| A0001 | PASS | PASS | PASS | PASS | PASS | PASS | PASS | PASS | PASS | APROVADA |
| A0002 | PASS | PASS | PASS | PASS | PASS | PASS | PASS | PASS | PASS | APROVADA |
| A0003 | PASS | PASS | PASS | PASS | PASS | PASS | PASS | PASS | PASS | APROVADA |
| A0004 | PASS | PASS | PASS | PASS | PASS | PASS | PASS | PASS | PASS | APROVADA |
| A0005 | PASS | PASS | PASS | PASS | PASS | PASS | PASS após correção | PASS | PASS | APROVADA |
| A0006 | PASS | PASS | PASS | PASS | PASS | PASS | PASS | PASS | PASS/FALLBACK | APROVADA |
| A0007 | PASS | PASS | PASS | PASS | PASS | PASS | PASS | PASS | PASS | APROVADA |
| A0008 | PASS | PASS | PASS | PASS | PASS | PASS | PASS | PASS | PASS | APROVADA |
| A0009 | PASS | PASS | PASS | PASS | PASS | PASS | PASS | PASS | PASS | APROVADA |
| A0010 | PASS | PASS | PASS | PASS | PASS | PASS | PASS após correção | PASS | PASS/FALLBACK | APROVADA |
| A0011 | PASS | PASS | PASS | PASS | PASS | PASS | PASS após correção | PASS | PASS | APROVADA |
| A0012 | PASS | PASS | PASS | PASS | PASS | PASS | PASS após correção + re-fetch | PASS | PASS/FALLBACK | APROVADA |
| A0013 | PASS | PASS | PASS | PASS | PASS | PASS | PASS | PASS | PASS | APROVADA |
| A0014 | PASS | PASS | PASS | PASS | PASS | PASS | PASS | PASS | PASS | APROVADA |
| A0015 | PASS | PASS | PASS | PASS | PASS | PASS | PASS | PASS | PASS | APROVADA |
| A0016 | PASS | PASS | PASS | PASS | PASS | PASS | PASS | PASS | PASS | APROVADA |
| A0017 | PASS | PASS | PASS | PASS | PASS | PASS | PASS | PASS | PASS/FALLBACK | APROVADA |
| A0018 | PASS | PASS | PASS | PASS | PASS | PASS | PASS | PASS | PASS | APROVADA |
| A0019 | PASS | PASS | PASS | PASS | PASS | PASS | PASS | PASS | PASS | APROVADA |
| A0020 | PASS | PASS | PASS | PASS | PASS | PASS | PASS | PASS | PASS | APROVADA |

## Correções exigidas pela auditoria

### A0001, A0007, A0013 e A0019 — classificação de arma

O design anterior declarava tags fallback `rpgskilltree:swords`, `axes`, `spears` e `daggers` que não existiam e não possuíam contrato versionado. Isso contrariava provider-native first e criava uma promessa de integração não materializada. O Notion foi alterado para: Epic Fight primeiro; se não houver classificação server-side segura, a perk fica inativa para aquele item. É proibido inferir categoria por nome, material, aparência, dano, velocidade ou alcance.

### A0004 e A0016 — stagger pesado

A lacuna técnica foi fechada com `EpicFightEventHooks.Entity.ON_STUNNED`. O adapter aceita somente `StunType.LONG`, `KNOCKDOWN` e `NEUTRALIZE`, exige fonte hostil e então aplica as perdas já definidas no policy. Não usa knockback, animação ou dano bruto como proxy.

### A0005 — Abertura de Guarda

O policy anterior tornava inalcançável o fallback declarado. O contrato corrigido distingue duas rotas:

1. guarda/postura observável e ativa: impacto/pressão e penetração podem ser aplicados conforme hooks disponíveis;
2. guarda/postura não observável: somente defesa física server-side comprovável autoriza penetração-only.

Se o provider consegue observar que o alvo não está defendendo, Armor não vira atalho para ativar a perk.

### A0010 — Pressão do Carrasco

O fallback genérico sem receipt real foi removido. Fúria só é concedida por resultado direto, hostil, com dano confirmado, autoria real e machado provider-native. Qualquer adapter futuro precisa provar os mesmos fatos e compartilhar a deduplicação; tentativa de ataque não conta.

### A0011 — Ruptura de Guarda

A condição “alvo classificado como pesado” foi removida porque nenhum provider obrigatório oferecia classificação inequívoca do alvo e o próprio fallback proibia heurísticas. A perk agora exige guarda/postura real ou, apenas quando esse estado não é observável, defesa física server-side comprovável para a parcela de penetração.

### A0012 — Frenesi do Saqueador

O antigo “parcel térmico causal” inexistente foi substituído por custos explícitos e o contrato foi endurecido para impedir benefício gratuito por falha tardia de integração:

- Frenesi em Fúria ≥75 somente com bridge Cold Sweat CORE operacional;
- em cada `DELIVER_DAMAGE_PRE` direto/hostil/elegível, o runtime tenta primeiro aplicar +1,5 em Cold Sweat `CORE`;
- somente se essa escrita realmente retornar sucesso aplica +0,015 exhaustion e autoriza +10% de impacto;
- em Fúria 100, após o mesmo pagamento corporal confirmado, o próximo PRE elegível pode gastar atomicamente 40 para total +20% impacto e, com guarda/postura nativa, total +40% pressão de guarda;
- o pico não depende de “ataque pesado” nem de heurística equivalente;
- se A0011 for elegível abaixo de 100 e seu gasto de 20 derrubaria Fúria abaixo de 75, A0012 não cobra custo corporal naquele hit porque nenhum benefício de Frenesi seria autorizado;
- ao cruzar de ≥75 para <75 enquanto o bridge CORE está operacional: Queda de Ritmo por 6/5/4 s, −15% em `epicfight:stamina_regen`;
- nenhuma sede é inferida de exhaustion e nenhum segundo recurso térmico é criado.

### Mastery Epic Fight — correção sistêmica exigida pelos gates

O adapter anterior concedia Mastery a cada hit com dano, permitindo farm repetitivo. Isso foi removido.

O contrato usa `DiscoveryProgress`, já persistido no estado canônico:

- hit comum repetido: **0 Mastery**;
- primeira interação ofensiva válida contra cada tipo hostil por categoria de arma: milestone único de +10 à categoria e +5 à lane genérica de arma;
- seis tipos hostis inéditos permitem atingir 60; oito, 80; dez, 100;
- skills de stamina não-guard contribuem uma única vez por `skillId`;
- `guard` exige uso de skill de stamina realmente pagável enquanto Epic Fight expõe um alvo hostil vivo; cada tipo hostil distinto contribui uma única vez com +10 `epicfight:guard`, portanto seis descobertas atingem 60 e oito atingem 80 sem permitir spam do mesmo alvo/tipo;
- dodge provider-native bem-sucedido contribui uma única vez;
- proc-depth >0 não gera Mastery.

Mastery já persistida em mundos existentes não é apagada retroativamente; apenas a geração futura passa pelo contrato anti-farm.

## Casos fail-closed aprovados

- **A0006:** somente receipts de defesa técnica realmente confirmados podem armar Riposta; `ON_DODGE` está provado. Outros tipos de defesa podem ser integrados depois, mas sua ausência não é substituída por heurística.
- **A0017:** o componente de redução do deslocamento ofensivo exige estado provider-native de movimento ofensivo. Enquanto esse receipt não existir, a janela + impacto/pressão funcionam e somente a redução de deslocamento é omitida. Esse é o fallback explicitamente aprovado no Notion.
- **Classificação externa de armas:** ausência de classificação segura desativa a perk para o item; não cria tags fictícias.
- **A0012 por evento:** falha de versão/API/escrita CORE deixa o pacote ofensivo inativo naquele PRE e não consome Fúria do pico.

## Testes de regressão obrigatórios deste lote

- A0005: defesa nativa, alvo observavelmente não defendendo e fallback Armor-only quando defesa não é observável;
- A0011: defesa nativa, rejeição do alvo desprotegido e fallback Armor-only;
- A0012: fail-closed sem receipt de custo corporal, baseline, pico de Fúria, gasto atômico após custo e transição de Queda de Ritmo;
- A0004/A0016: perda de cargas por stagger forte confirmado;
- Mastery: hit repetível sem milestone não concede XP; milestone finito concede valores esperados; guard 60/80 é alcançável por 6/8 tipos hostis distintos; proc-depth não concede XP;
- build NeoForge e dedicated-server smoke antes do merge.

## Conclusão

Após as correções acima, **A0001–A0020 atendem aos nove eixos obrigatórios de design e aos critérios técnicos aplicáveis ao recorte**. Não há pendência de design bloqueante dentro deste intervalo. A0017 permanece deliberadamente em seu fallback canônico para redução de deslocamento; isso não autoriza heurística e não bloqueia a aprovação da perk.

O status `IMPLEMENTAÇÃO CONFIRMADA` depende do CI final verde e do merge da PR de auditoria na `main`.