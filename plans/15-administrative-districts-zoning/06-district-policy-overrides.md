# 15.06 — Overrides de política distrital

## Objetivo

Permitir que uma lei geral seja especializada por território sem duplicar o sistema jurídico.

O Stage 15 persiste apenas referências/valores territoriais; Stage 17 é autoridade da validade legal.

## Precedência aprovada

```text
lei global/realm/colony
→ política distrital
→ decreto específico/temporário
```

A camada mais específica pode substituir parâmetros permitidos. Cláusulas marcadas como não delegáveis pelo Stage 17 não podem ser anuladas por distrito/decreto.

## Exemplos de parâmetros

- taxa de imposto local;
- subsídio comercial/industrial;
- prioridade térmica;
- horário de comércio;
- racionamento;
- restrição de zoning;
- prioridade de segurança/saúde.

## Revision

Toda alteração incrementa revision e invalida caches jurídicos/econômicos apenas nos districts afetados.

## Testes

- override permitido/proibido;
- decreto vence política quando autorizado;
- expiração restaura política anterior;
- mudança de distrito recalcula contexto;
- ausência do Stage 17 mantém policy data inerte e segura.

## Acceptance

Nenhum consumidor precisa conhecer a cadeia inteira: consulta o resolver canônico e recebe a policy efetiva do local.