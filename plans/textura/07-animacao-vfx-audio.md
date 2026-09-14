# 07 — Animação, VFX, partículas e áudio

## Estado desta auditoria

Nenhum plano ativo de apresentação auditado em 2026-09-13 fornece especificação normativa dedicada para clips, rigs, curvas, VFX, partículas, cues sonoros ou arquivos de áudio do RPG Skill Tree.

Portanto, esta migração **não cria** animações, partículas, sons, timings ou estilos fictícios. Este arquivo estabelece a authority para trabalho futuro.

## Ownership

### Engenharia fornece

Quando uma feature realmente precisar de feedback audiovisual, o plano de engenharia deve fornecer o evento/estado causal e, conforme aplicável:

- ID estável do evento/estado;
- side em que o feedback pode ser disparado;
- entidade/posição/anchor permitido;
- início/fim funcionais quando relevantes;
- informação de cancelamento/falha;
- dedupe/root action quando necessário;
- provider que já possui animação/VFX/áudio nativo;
- restrições de performance/rede comprovadas;
- fallback funcional se a apresentação não existir.

### Textura/Apresentação fornece

Após esse handoff, esta authority pode definir:

- clip/rig/pose;
- blend/transição e curvas cosméticas;
- timing de apresentação que não altere janela funcional;
- VFX/partículas, forma, densidade, paleta e composição;
- attach points/anchors visuais;
- cue de áudio, variações, mix e espacialização;
- sincronização estética com um evento funcional já confirmado;
- fallback visual/sonoro;
- validação em client real.

## Provider-native first

Se Epic Fight, Iron's, Ars, Create ou outro provider já for authority de animação, renderer, partícula ou som para a ação real, o default é usar/respeitar a apresentação nativa.

Uma substituição própria exige seam comprovado e design explícito. Não usar um clip customizado para inferir hit, parry, cast concluído ou outro receipt de gameplay.

## Causalidade

- VFX/som de sucesso só deve ocorrer a partir do estado/evento que realmente significa sucesso.
- PRE/prediction pode ter feedback distinto quando a engenharia expuser esse estado, mas não pode fingir confirmação.
- evento cancelado não pode manter feedback que semanticamente afirme um resultado inexistente, salvo se a apresentação for explicitamente de tentativa.
- efeitos derivados não devem duplicar feedback do root sem regra definida.

## Performance

Não congelar números arbitrários nesta migração. Budgets de partículas, vozes simultâneas, duração, distância e LOD devem ser medidos no contexto real antes de virarem gates.

## Artefatos futuros

Quando existirem, registrar aqui ou em subplanos derivados:

- lista de clips/rigs;
- cue sheet;
- atlas/particle textures;
- material/shader requirements;
- anchors por entidade/item;
- Golden Samples;
- matriz provider presente/ausente;
- client smoke visual/audiovisual.

## Gate

Nenhuma feature fica funcionalmente bloqueada apenas porque um asset audiovisual ainda não existe, salvo se o design aprovado declarar explicitamente que a apresentação é parte indispensável da experiência e a engenharia possuir fallback seguro para desenvolvimento.
