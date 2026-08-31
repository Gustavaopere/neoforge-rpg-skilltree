# A0106 — Guarda de Emergência

## Estado

- **Design:** APROVADO após correção de boundary em 2026-08-31.
- **Notion:** `3c569db9-f0db-813c-8479-df204149bf19`; corrigido e verificado pós-escrita.
- **Runtime:** resolver/state ainda precisam ser implementados; o hook NeoForge necessário já existe.

## Contrato canônico

- Gateway VITALITY + A0104 + A0105 + A0095 Tenacidade ≥3.
- No `LivingDamageEvent.Pre`, se o dano hostil elegível disponível nesse boundary projetar vida <15%, ativa imediatamente.
- O golpe gatilho e eventos elegíveis durante 60 ticks recebem multiplicador 0,65 uma vez por evento.
- A ativação cria 1 token de Salvaguarda Fatal; se depois do 0,65 o dano ainda seria letal, consumir o token e limitar para deixar exatamente 1 HP.
- Cooldown 3600 ticks inicia na ativação.

## Ordem exata do NeoForge

No NeoForge 1.21.1, `LivingDamageEvent.Pre` ocorre depois das reduções anteriores de armor/enchantments/mob effects/innate e antes de absorption/health. Portanto A0106 **não promete prever absorption futura**: o limiar e o clamp são definidos sobre o dano de vida disponível nesse estágio.

A0106 deve executar depois dos reducers RPG tipados que precisam precedê-la. O golpe gatilho pode consumir imediatamente a única Salvaguarda. Após o token ser gasto, o multiplicador continua até o fim da janela.

## Exclusões

Bypass/inevitável explicitamente excluído, Void/final kill, `/kill`, `BLOOD_MAGIC_COST`, self/resource cost e morte fisiológica não ativam nem consomem o token. Não existe ressurreição pós-morte.

## Pendências para Chat 2

- `P-A0106-01`: implementar ordem no Pre, state 60 ticks, cooldown persistente e multiplicador único.
- `P-A0106-02`: token fatal único, clamp 1 HP e testes de trigger lethality, absorption, bypass, lifecycle e dedup.

## Nove eixos obrigatórios

| Eixo | Resultado | Decisão |
|---|---|---|
| Dependências/gates | PASS | convergência A0104/A0105/A0095. |
| Integração global | PASS | pipeline único, sem ressurreição. |
| Qualidade/identidade | PASS | capstone de emergência. |
| Topologia | PASS | terminal digno de camada 5. |
| Especializações | PASS | terminal exterior governado. |
| PT-BR | PASS | ordem e token explícitos. |
| Notion | PASS | P-0034 corrigida. |
| NeoVitae | PASS | ausente. |
| Providers | PASS | boundary NeoForge comprovado. |

Os 18 critérios passam; P-0034 deixa de ser blocker de API e vira tarefa concreta de implementação.