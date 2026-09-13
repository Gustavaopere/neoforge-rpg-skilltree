<!-- Atualização incremental de 2026-08-30. Fonte canônica no Notion: https://app.notion.com/p/3c569db9f0db81a69e3ee1232ee636ff -->

[← Índice do guia](README.md)

# 20. Mobstein — boundary: não é provider tecnológico

## Mobstein : Revive animals and necromancy! — 5.4.4

`mobstein-5.4.4-neoforge-1.21.1.jar`

Mobstein possui blocos/dispositivos com apresentação de laboratório — **Clinical Stretch**, **Surgery Stretch** e **Subject Assembly Machine** — mas isso **não o transforma em provider tecnológico** do pack.

A função documentada desses elementos é servir ao sistema de ressurreição corporal, partes/órgãos, experimentos, mannequins e mobs ressuscitados. A página oficial não estabelece, para a versão auditada, um contrato de FE, SU, rede, automação industrial, processamento Create, AE2 ou Oritech associado a essas estações.

## Classificação para perks

No eixo Tecnologia, Mobstein deve ser classificado como:

- **NÃO APLICÁVEL** por padrão; ou
- **SEM HOOK SEGURO** se uma futura perk quiser transformar a fantasia de laboratório em bridge tecnológica sem API comprovada.

É proibido inferir energia, eficiência industrial, throughput, automação, manutenção ou integração Create/AE2/Oritech apenas porque um bloco possui `Machine` ou `Stretch` no nome ou aparência de laboratório.

Perks sobre ressurreição, corpos/órgãos, experimentos, companions/bodyguards, estruturas e boss devem tratar **Mobstein como provider de Gameplay/Magia**, conforme os capítulos correspondentes. Uma futura bridge tecnológica só pode existir depois de contrato/API real, authority definida e fail-closed quando o adapter estiver ausente.

## Fonte auditada

- CurseForge oficial: `Mobstein : Revive animals and necromancy!`, projeto 1193873.
- Release NeoForge 1.21.1: `mobstein-5.4.4-neoforge-1.21.1.jar`, publicada em 04/05/2026.
