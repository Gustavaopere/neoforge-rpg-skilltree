# Fronteira Engenharia ↔ Textura/Apresentação

## Regra normativa

**Engenharia produz semântica; Textura produz apresentação.**

Nenhuma decisão de renderer, asset, clip, partícula, som ou layout pode alterar o resultado funcional definido pelo servidor ou pelo provider autoritativo.

## Matriz de ownership

| Assunto | Engenharia | Textura/Apresentação |
| --- | --- | --- |
| Estado de gameplay | autoridade, transições, validação | representação do estado recebido |
| UI | read models, widgets funcionais, input, requests, sync | layout, chrome, sprites, ícones, tipografia, motion |
| HUD | dado permitido, frequência de atualização, side | composição, hierarquia, gauges, animação visual |
| Tooltips | conteúdo semântico, valores, localization keys | layout, badges, ícones, espaçamento, legibilidade |
| Modelos/previews | segurança de construção/render, fallback técnico | composição, câmera, enquadramento, asset e apresentação |
| Animação | evento/estado funcional, timing que afeta gameplay quando houver | clips, rig, curvas, transições e motion cosmético |
| VFX/partículas | evento que autoriza disparo, side e limites técnicos | identidade visual, paleta, forma, densidade e composição |
| Áudio/SFX | trigger funcional, side, sincronização e limites | identidade sonora, arquivos, mix, cue sheet e variações |
| Cartografia | dados visíveis, anti-cheat, IDs/reconciliação | ícones, overlays, estilos e hierarquia visual |
| Localização | keys, argumentos, fallback semântico, validator | tipografia, quebra, contraste e densidade textual |
| Acessibilidade | estados semânticos alternativos e dados necessários | contraste, redundância visual/textual, escala e legibilidade |

## Handoff mínimo de Engenharia para Textura

Toda superfície de apresentação deve conseguir responder, conforme aplicável:

- `surface_id` — tela/HUD/overlay afetado;
- `states[]` — estados funcionais possíveis;
- `events[]` — eventos que podem disparar feedback;
- `fields[]` — dados presentation-safe disponíveis;
- `actions[]` — intenções que o cliente pode enviar;
- `authority` — quem valida a ação/estado;
- `provider_profile` — provider/adapter quando a superfície depender de integração externa;
- `anchors[]` — pontos de ligação para model/animation/VFX/audio quando existirem;
- `fallback` — comportamento funcional se asset/renderer/provider visual faltar;
- `performance_budget` — limites técnicos quando comprovados/medidos;
- `security_notes` — informações que não podem chegar ao cliente.

Campos inexistentes não devem ser inventados para satisfazer o handoff.

## Handoff mínimo de Textura para Engenharia

A entrega visual deve declarar, quando aplicável:

- assets e namespaces;
- estados visuais cobertos;
- resolução/dimensões e comportamento de escala;
- anchors/slots esperados;
- animações/clips e condições puramente de apresentação;
- VFX/partículas e condições de apresentação;
- cues de áudio e assets associados;
- fallback visual/audiovisual;
- requisitos de acessibilidade;
- restrições de performance conhecidas;
- checklist de Golden Samples/client smoke visual.

## Regras de segurança e causalidade

1. Renderer, sprite, animação, partícula e som nunca decidem dano, custo, cooldown, unlock, recompensa, progressão ou persistência.
2. Ausência de asset não muda o resultado de gameplay.
3. Um feedback audiovisual não pode ser usado como receipt de gameplay se o evento funcional real não existir.
4. Eventos secundários/derivados só recebem apresentação quando a engenharia os expõe de forma causalmente identificável.
5. Dados secretos continuam filtrados antes de chegar à camada de apresentação.
6. Provider-native continua primeiro: a camada visual não substitui um renderer/animation system do provider sem design explícito e seam comprovado.
7. Fallback visual é seguro e cosmético; não pode criar um bônus genérico para esconder uma integração funcional ausente.

## Arquivos mistos

Quando um plano exigir engenharia e apresentação:

- o estágio numerado mantém o contrato funcional;
- `plans/textura/` mantém a apresentação;
- ambos se referenciam por caminho;
- nenhuma cópia divergente dos mesmos valores funcionais deve ser mantida;
- mudanças de semântica voltam ao plano de engenharia antes de qualquer atualização estética dependente.

## Testes

Engenharia valida contratos, authority, side, rede, persistência, causalidade e segurança.

Textura valida aparência, leitura dos estados, escalabilidade, clipping, contraste, consistência de asset, animação/VFX/áudio e client smoke visual.

Client smoke técnico continua sendo responsabilidade compartilhada quando a falha é classloading/resource missing/exception. Julgamento estético não substitui teste funcional e teste funcional não substitui validação visual.
