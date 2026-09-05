# 19.07 — Frio, saúde, produtividade e morte

## Jogador

Quando Cold Sweat está presente e é o provider corporal canônico, Stage 19 aplica apenas integração permitida (fonte de calor/ambiente/insulation effect conforme API), sem manter segunda temperatura corporal.

## Cidadãos MineColonies

`CitizenColdExposure` pode derivar de:

- prédio/posição atual;
- thermal state;
- tempo acumulado em exposição;
- abrigo/serviço;
- proteção provider comprovada.

Atualização ocorre em períodos bounded, não todo tick por cidadão.

## Consequências graduais

Estados configuráveis:

```text
SAFE → COLD → SEVERE → CRITICAL
```

Podem reduzir produtividade, aumentar necessidade médica e, em exposição crítica prolongada, causar dano/morte somente através de hook seguro e balanceado. Não matar instantaneamente por uma falha de rede de poucos segundos.

## Recuperação

Heat/rest reduz exposure progressivamente; não reset mágico imediato salvo treatment explícito.

## Proteções

Crianças/NPCs especiais e providers com health rules próprias exigem adapter antes de aplicar dano.

## Testes

- citizen heated/unheated;
- gradual accumulation/recovery;
- productivity modifier lifecycle;
- Cold Sweat present/absent;
- no duplicate player temperature model;
- restart exposure persistence policy.

## Acceptance

Falta de calor importa, mas é previsível, gradual e integrada às autoridades corretas.