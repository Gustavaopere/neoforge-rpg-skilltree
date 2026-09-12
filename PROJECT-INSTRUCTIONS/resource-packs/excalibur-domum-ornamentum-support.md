# Excalibur | Domum Ornamentum Support

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3d669db9f0db81198ac2f98c6f960e94
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Tipo de conteúdo:** Resource Pack, Addon
- **Arquivo:** `Excalibur_Domum_Ornamentum Support_1.21.1_v1.0.zip`
- **Versão 1.21.1:** 1.0
- **Data da exportação:** 2026-09-11

## Autoridade e limite físico na exportação

- O dossiê Notion registra `Excalibur_Domum_Ornamentum Support_1.21.1_v1.0.zip` como fisicamente confirmado por captura da pasta Resource Packs do perfil em 08/09/2026.
- A busca atual na Biblioteca não recuperou diretamente esse ZIP/captura; portanto a presença/versão do resource pack é preservada conforme a procedência do dossiê, sem converter a modlist JAR-centric em inventário de resource packs.
- A modlist física de 08/09/2026 confirma `domum-ornamentum-1.0.236-snapshot-main.jar`, mod id `domum_ornamentum`, runtime `1.0.236-snapshot`.
- O support pack é explicitamente WIP; “a maioria dos blocos” não é convertido em cobertura total. A combinação com o snapshot físico permanece sujeita a QA visual.

## Propriedades do banco

- **Mod:** Excalibur | Domum Ornamentum Support
- **Arquivo JAR:** `Excalibur_Domum_Ornamentum Support_1.21.1_v1.0.zip`
- **Tipo de conteúdo:** Resource Pack, Addon
- **Versão 1.21.1:** 1.0
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Sem decisão
- **Categoria:** Visual, Compat
- **Função:** Support pack visual 16x que retexturiza a maioria dos blocos de Domum Ornamentum para combinar com Excalibur; não altera gameplay.
- **Dependências:** Uso visual pretendido: Excalibur base + Domum Ornamentum. Stack físico atual: Excalibur V26.1_01 e Domum Ornamentum 1.0.236-snapshot. O pack oficial exige prioridade acima do Excalibur.
- **Sobreposição:** Sobrepõe apenas assets Domum Ornamentum presentes no ZIP. Deve carregar acima do Excalibur; outros packs com os mesmos asset paths podem substituí-lo conforme prioridade.
- **Compatibilidade/Riscos:** Projeto WIP: cobertura não é 100% garantida. Risco de assets sem textura devido ao Domum Ornamentum 1.0.236-snapshot, load order incorreto e colisão com outros overrides do mesmo namespace.
- **Observações:** Arquivo instalado `Excalibur_Domum_Ornamentum Support_1.21.1_v1.0.zip`. Upstream marca o support pack como work in progress e afirma que a maioria dos blocos foi retexturizada.
- **Procedência:** Captura CurseForge do perfil RPG em 08/09/2026 + modlist física atual + CurseForge oficial da build v1.0 para Minecraft 1.21.1.
- **Fonte:** https://www.curseforge.com/minecraft/texture-packs/excalibur-domum-ornamentum-support
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 11/09/2026 — dossiê de resource pack reconstruído; cobertura WIP, alvo Domum Ornamentum 1.0.236-snapshot, load order, lifecycle visual, riscos e QA catalogados.
- **Histórico da decisão:**

# Dossiê operacional — padrão Alex's Mobs

> **Resource pack físico confirmado no dossiê de origem:** `Excalibur_Domum_Ornamentum Support_1.21.1_v1.0.zip`, versão catalogada `1.0`. Página oficial classifica a build como Release para Minecraft 1.21.1. O projeto é um support pack visual para Domum Ornamentum e permanece explicitamente **work in progress**.

## 1. Papel e authority
Excalibur | Domum Ornamentum Support não adiciona gameplay, registries, recipes ou lógica de servidor. Sua função é substituir assets visuais de **Domum Ornamentum** para aproximá-los da direção medieval 16x do **Excalibur**.
Domum Ornamentum continua authority de blocos, estados, modelos lógicos e comportamento. Excalibur continua o pack visual base. Este addon apenas fornece assets de override.

## 2. Cobertura confirmada
A descrição oficial informa que **a maioria dos blocos** de Domum Ornamentum foi retexturizada para combinar com Excalibur. O próprio autor marca o projeto como WIP; portanto não tratar a cobertura como 100% completa e não presumir que todo material, combinação dinâmica ou asset novo do mod já possua textura própria.

## 3. Stack físico atual
O pack mantém `domum-ornamentum-1.0.236-snapshot-main.jar`, runtime `1.0.236-snapshot`.
A diferença entre uma build snapshot do mod e um support pack WIP cria uma superfície real de drift: blocos/assets adicionados ou renomeados no snapshot podem cair no visual padrão do mod.

## 4. Load order
A instalação oficial exige colocar o support pack **acima do Excalibur** na lista de Resource Packs. Essa prioridade é parte do contract visual: se ficar abaixo, assets do Excalibur ou de outro pack de maior prioridade podem vencer o override.
Não há evidência de uma ordem adicional obrigatória com MineColonies além da presença de Domum Ornamentum.

## 5. Client e resource reload
É conteúdo visual client-side. Aplicar/remover/reordenar o pack provoca resource reload e deve afetar somente models/textures/assets do cliente.
Não deve modificar save, inventários, blocos existentes ou state de colônia. Um problema visual após reload não autoriza alterar dados de gameplay.

## 6. Sobreposição
Pode sobrepor assets de outros packs que também retexturizem Domum Ornamentum. A prioridade final é determinada pela ordem dos resource packs para cada caminho de asset coincidente.
O objetivo declarado é complementar Excalibur, não substituir Domum Ornamentum nem o resource pack base inteiro.

## 7. Riscos
1. Cobertura incompleta por status WIP.
2. Snapshot 1.0.236 introduzir assets sem equivalente no support pack v1.0.
3. Load order incorreto impedir overrides.
4. Outro pack sobrescrever os mesmos caminhos de textura/modelo.
5. Assets dinâmicos/combinatórios de Domum Ornamentum exibirem mistura de estilos.
6. Resource reload deixar cache/render inconsistente até recarregar o mundo/cliente.

## 8. Matriz de testes
- [ ] Confirmar pack acima de Excalibur.
- [ ] Abrir inventário/creative e conferir amostra representativa dos blocos Domum Ornamentum.
- [ ] Conferir blocos colocados em construções MineColonies/Domum Ornamentum.
- [ ] Testar diferentes materiais/variantes ornamentais para detectar fallback visual.
- [ ] Reordenar pack abaixo/acima de Excalibur e confirmar precedência esperada.
- [ ] Executar resource reload sem missing-texture/purple-black assets.
- [ ] Conferir se atualização futura do snapshot Domum cria novos assets sem cobertura.

Nenhum teste foi marcado como aprovado nesta auditoria documental.

## 9. Evidências e limite
- captura CurseForge do perfil do usuário em 08/09/2026: arquivo instalado v1.0;
- modlist física atual: Domum Ornamentum `1.0.236-snapshot`;
- CurseForge oficial do support pack: v1.0 para 1.21.1, WIP, maioria dos blocos retexturizada e instrução explícita de load order acima do Excalibur.
Não foi auditado o conteúdo interno do ZIP; contagem exata de assets permanece não confirmada.

> Boundary canônico: **Domum Ornamentum controla o conteúdo; este pack controla apenas a aparência dos assets que efetivamente substitui**.
