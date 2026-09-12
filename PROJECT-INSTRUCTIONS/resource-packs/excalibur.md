# Excalibur

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3d669db9f0db81bc801ec01e8cd081a2
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Tipo de conteúdo:** Resource Pack
- **Arquivo:** `Excalibur_V26.1_01.zip`
- **Versão 1.21.1:** V26.1_01
- **Data da exportação:** 2026-09-11

## Autoridade e limite físico na exportação

- O dossiê Notion registra `Excalibur_V26.1_01.zip` como fisicamente confirmado por captura da pasta Resource Packs do perfil em 08/09/2026.
- A busca atual na Biblioteca não recuperou diretamente esse ZIP/captura; portanto a presença/versão do resource pack é preservada conforme a procedência do dossiê, sem converter a modlist JAR-centric em inventário de resource packs.
- O stack atual possui diversos support packs que devem prevalecer sobre a base Excalibur nos assets coincidentes; a ordem de prioridade é parte do contrato operacional do dossiê.

## Propriedades do banco

- **Mod:** Excalibur
- **Arquivo JAR:** `Excalibur_V26.1_01.zip`
- **Tipo de conteúdo:** Resource Pack
- **Versão 1.21.1:** V26.1_01
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Visual
- **Função:** Resource pack base 16x de direção medieval/adventure que redefine a identidade visual global do pack, com textures, variantes, melhorias 3D, fonte custom e random mobs.
- **Dependências:** Resource pack base; não depende de mod para funcionar. No stack atual, seus support packs devem carregar acima dele. V26.1_01 é oficialmente listado para Minecraft 1.21.1.
- **Sobreposição:** É a camada visual base. Clarent e support packs específicos intencionalmente o sobrescrevem; a ordem de prioridade precisa manter Excalibur abaixo dessas camadas para evitar perda de compatibilidade.
- **Compatibilidade/Riscos:** Riscos de conflito por prioridade com dezenas de support packs. Drift documentado: Clarent 12110 v3 foi feito para Excalibur 1.21.10, e o Fresh Animations Patch atual recomenda Excalibur 1.21.11 para MC 1.21.x, enquanto o perfil usa V26.1_01.
- **Observações:** Arquivo instalado `Excalibur_V26.1_01.zip`, release de 06/05/2026. O projeto declara substituição de ~99% das textures, random/alternate blocks, melhorias 3D, custom font e random mobs; V26.1_01 adiciona baby zombie villagers e inclui fixes/updates da linha 26.1.
- **Procedência:** Captura CurseForge do perfil RPG em 08/09/2026 + CurseForge oficial Excalibur V26.1_01/file support para Minecraft 1.21.1.
- **Fonte:** https://www.curseforge.com/minecraft/texture-packs/excalibur
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 11/09/2026 — dossiê base Excalibur reconstruído; V26.1_01, cobertura 16x/medieval, random/3D/font/mobs, load-order authority, drift Clarent/Fresh Animations e QA catalogados.
- **Histórico da decisão:**

# Dossiê operacional — padrão Alex's Mobs

> **Resource pack base confirmado no dossiê de origem:** `Excalibur_V26.1_01.zip`, versão catalogada `V26.1_01`, Release oficial de 06/05/2026 e listada pelo projeto como compatível com Minecraft 1.21.1.

## 1. Papel e authority visual
Excalibur é a **camada visual base** do stack. Ele não altera gameplay, registries, recipes ou save; define a estética medieval/adventure sobre a qual os support packs específicos aplicam overrides.

## 2. Cobertura publicada
O upstream descreve aproximadamente **99% das textures** substituídas e destaca random/alternate blocks, melhorias 3D de blocos, custom font e random mobs. Esses recursos formam a identidade visual global; 99% não deve ser convertido em cobertura absoluta.

## 3. Build V26.1_01
A release V26.1_01 está publicada para a linha moderna e é listada como suportando 1.21.1. A linha 26.1 inclui fixes de modelos/render e ajustes de blocks/entities/items/UI; V26.1_01 também registra baby zombie villagers.

## 4. Load-order authority
Excalibur deve ficar **abaixo** de seus support packs, porque eles precisam vencer seus assets genéricos para aplicar compatibilidade de mods. Clarent, quando usado, deve ficar diretamente acima do Excalibur; support packs específicos acima do Clarent conforme documentação desse patch.

## 5. Drift atual do stack
Há duas divergências documentadas:
- Clarent `12110_1202+_v3` foi feito para Excalibur 1.21.10, não V26.1_01;
- o Fresh Animations Patch atual recomenda Excalibur 1.21.11 para Minecraft 1.21.x, enquanto o perfil mantém V26.1_01.
Isso não prova quebra, mas exige QA visual do stack real.

## 6. Client e resource reload
Todos os efeitos são client-side. Reordenar packs provoca resource reload; save, entities e inventories não devem ser modificados. Random variants e custom models/fonts precisam convergir após reload/relog.

## 7. Sobreposição e riscos
1. Support pack abaixo do Excalibur perder override.
2. Clarent antigo reverter assets mais novos de V26.1_01.
3. Fresh Animations Patch esperar base diferente.
4. Dois support packs alterarem o mesmo path.
5. Custom font/GUI comprometer legibilidade com outro GUI pack.
6. Random/3D models conflitar com model packs específicos.

## 8. Matriz de testes
- [ ] Confirmar Excalibur como base abaixo dos addons.
- [ ] Comparar Clarent on/off para regressões de V26.1_01.
- [ ] Validar random blocks/mobs e models 3D.
- [ ] Validar custom font e GUI readability.
- [ ] Resource reload sem missing assets.
- [ ] Smoke visual em vanilla + mods sem support específico.

Nenhum teste foi marcado como aprovado.

## 9. Evidências e limite
CurseForge oficial confirma V26.1_01, compatibilidade listada com 1.21.1 e o escopo visual geral. Não foi feito inventário interno dos ~99% de assets; o percentual permanece claim do upstream.

> Boundary canônico: **Excalibur é a base visual; support packs e patches só devem sobrescrever assets, nunca assumir ownership de gameplay**.
